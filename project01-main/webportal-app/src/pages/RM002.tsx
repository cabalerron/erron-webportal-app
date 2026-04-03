/*
 * RM002
 * Used to register a role
 *
 * v 00.001 - 10/29/2024
 *
 * PIC: emonteverde
 * 
 */
import { FC, useCallback, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import apiConfig from '../common/apiAxiosConfig'
import {
  Checkbox,
  Button,
  TextField,
  FormControl,
  Box,
  InputAdornment,
  Stack,
  List,
  ListItem,
  ListItemText 
} from "@mui/material";
import { Shield, Label} from '@mui/icons-material';
import { getMessage } from "../common/MessageUtil";
import { Function, RoleItem } from "../types/DataType"; 
import { RootState } from '../redux/store';
import { setFunction } from "../redux/FunctionReducer";
import PanelHeader from '../component/PanelHeader';
import DialogBox from "../component/DialogBox";
import { routes, labelText } from '../common/appConstant';
import { useForm, SubmitHandler } from 'react-hook-form';
import '../style/RM001.css';

export const RoleAdd: FC = () => {
  
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const loggedInUsername = 
        useSelector((state: RootState) => state.params.param01);
  const loggedInUserRole = 
        useSelector((state: RootState) => state.params.param02);
  const [nextRoleId, setNextRoleId] = useState("");

  const {
		register,
		handleSubmit,
		setValue,
		getValues,
    reset,
		formState: { errors },
	} = useForm<RoleItem>();

  //Back Button Start
  const handleBackClick = () => {
    navigate(-1); 
  };
  //Back Button End

  //Dialog Message Start
  const [show, setShow] = useState(false);
  const [dialogMessage, setDialogMessage] = useState("");
  const [confirmationMessage, setConfirmationMessage] = useState(false);
  const [isRegister, setIsRegister] = useState(false);

  const handleRegisterClick = () => {
    setIsRegister(true);
    setDialogMessage(getMessage('E_TSCL_01_0010', ["role ID: " + nextRoleId]));
    setConfirmationMessage(true);
    setShow(true);
  };

  const cancelRegistration = () => {
    setIsRegister(false);
    setDialogMessage(getMessage("I_TSCL_01_0025"));
    setConfirmationMessage(true);
    setShow(true);
  };

  const handleClose = () => {
    if(isRegister){
      navigate(routes.RM001);
    }
    setShow(false);
  };

  const handleYes = () => {
    if(isRegister){
      handleSubmit(handleRegister)();
    }
    else if(!isRegister){
      navigate(routes.RM001);
    }
    setShow(false);
  };
  //Dialog Message End

  //Get Function List
  const functionList = 
        useSelector((state: RootState) => state.function.functionList);
  const handleFunctionListWithId = useCallback((content: Function[] = []) => {
    const functionListWithId = content.map((func: Function) => ({
      id: func.functionId,
      ...func,
    }));
    dispatch(setFunction({ functionList: functionListWithId }));
  }, [dispatch]);

  const fetchFunction = useCallback(async () => {
    try {
      const response = await apiConfig.get('/role/initRegis');
      
      const data = response.data;
      setNextRoleId(data.roleId);
      setValue('roleId', data.roleId); 
      handleFunctionListWithId(data.functionList);
    } catch (error) {
      console.error('Error fetching functions:', error);
    }
  }, [handleFunctionListWithId, setValue]);

  useEffect(() => {
    fetchFunction();
  }, [fetchFunction, dispatch]);

  //Format Function List as multilevel numbering 
  const formatFunctionCode = (code: string): string => {
    return code.split('').join('.'); 
  };

  //Checkbox Behavior Start
  const [checked, setChecked] = useState<number[]>([]);

  const getPrimaryCode = (functionCode: string) => {
    const primary = functionCode.length === 2 
    ? functionCode 
    : functionCode.slice(0, -1);

    return primary;
  }

  const getSecondary = (functionCode: string) => {
    const primary = getPrimaryCode(functionCode);
    let secondaryIds: number[] = [];
    secondaryIds = functionList
          .filter(func => func.functionCode.startsWith(primary) && 
                func.functionCode !== primary )
          .map(func => parseInt(func.functionCode));
    return secondaryIds;
  }

  const handleToggle = (functionCode: string) => {
    const primary = getPrimaryCode(functionCode);
    const parseFunctionCode = parseInt(functionCode);
    const listViewCodes = functionList
                  .filter(func => func.functionName === "List View")
                  .map(func => func.functionCode);

    setChecked((prev) => {
      const isChecked = prev.includes(parseFunctionCode);
      const isPrimary = functionCode.length === 2;
      const secondary = getSecondary(functionCode);
      const sliced = functionCode.length > 2 ? 
            parseInt(functionCode.slice(0, -1)): parseFunctionCode;
      let updatedChecked = [...prev];
      
      if (isPrimary) {
        if(isChecked){
          //Primary Unchecked
          updatedChecked = updatedChecked.filter(code => 
                !getSecondary(functionCode).includes(code) && 
                code !== parseFunctionCode);
        }else{
          //Primary Checked
          updatedChecked = [...updatedChecked, parseFunctionCode, 
                ...getSecondary(functionCode)];
        }
      }else{
        if (isChecked) {
          //Secondary Unchecked
          updatedChecked = updatedChecked.filter(code => code !== 
                parseFunctionCode && code !== sliced);
        } else {
          //Secondary Checked
          if (!listViewCodes.includes(functionCode)) {
            const checkListView = listViewCodes.filter(code => 
                  code.startsWith(primary))[0] || '';
            updatedChecked = [...updatedChecked, parseFunctionCode, 
                  parseInt(checkListView)];
          }else{
            updatedChecked = [...updatedChecked, parseFunctionCode];
          }
        }
      }
      
      const isAllSecondaryChecked = secondary.every(code => 
            updatedChecked.includes(code));
      //if all Secondary is checked; Primary will be checked
      if(isAllSecondaryChecked){
        updatedChecked = [...updatedChecked, parseFunctionCode, sliced];
      }

      setChecked(updatedChecked);
      return updatedChecked
    });
  };
  //Checkbox Behavior End

  const handleRegister: SubmitHandler<RoleItem> = (data) => {
    if(checked.length === 0){
      setDialogMessage(getMessage("E_TSCL_01_0032", ["Permission"]));
      setConfirmationMessage(false); 
      setShow(true); 
      setIsRegister(false);
    }
    else{
      const checkedFunctionCodes = functionList
          .filter(func => checked.includes(parseInt(func.functionCode)) && 
                func.functionCode.length !== 2 )
          .map(func => func.functionCode); 

      const formData = new FormData();
      formData.append('loggedInUsername', loggedInUsername);
      formData.append('loggedInUserRole', loggedInUserRole);
      formData.append('roleId', data.roleId);
      formData.append('roleName', data.roleName);
      formData.append('roleShName', data.roleShName);
      formData.append('permissions', checkedFunctionCodes.join(','));

      const fetch = async () => {
        try {
          const response = await apiConfig.post('/role/registerRole', 
            formData, {
              headers: {
                'Content-Type': 'multipart/form-data',
              },
            });
          if (response.status === 200) {
            setShow(true);
            setConfirmationMessage(false);
            if(response.data.errCode === null){
              setDialogMessage(getMessage('E_TSCL_01_0023'));
              setIsRegister(true);
              reset();
            }else{
              setDialogMessage(getMessage(response.data.errCode, 
                    [response.data.errItem]));
              setIsRegister(false);
            }
            
          }
        } 
        catch (error: any) {
          setDialogMessage(getMessage(error.response.data));
          setShow(true);
          setConfirmationMessage(false);
          setIsRegister(false);
        }
      };
		  fetch();
    }
	};

  return (
    <div>
      <PanelHeader 
        showBackButton={true} 
        onBackClick={handleBackClick} 
        title="Role Registration"
      />
      <div className="webportal_screen">
      <FormControl fullWidth component="form" 
            onSubmit={handleSubmit(handleRegister)}>
        <Box className="form-box">
          <Stack sx={{m:3}}>
            <Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', 
                    gap: 5, mt:2, width: '100%'}}>
              <div>
                <TextField
                  id="txtRoleId"
                  label= {labelText.roleId}
                  variant="outlined"
                  fullWidth
                  disabled
                  defaultValue={getValues('roleId')}
                  slotProps={{
                    input: {
                      startAdornment: (
                        <InputAdornment position="start">
                          <Shield />
                        </InputAdornment>
                      ),
                    },
                  }}
                  {...register('roleId', {
                    required: {
                      value: true,
                      message: getMessage('E_TSCL_01_0006', ['Role ID']),
                    },
                    maxLength: {
                      value: 50,
                      message: getMessage('E_TSCL_01_0016', ['Role ID']),
                    },
                  })}
                />
              </div>
              <div>
                <TextField
                  id="txtRoleName"
                  label= {labelText.roleName}
                  variant="outlined"
                  fullWidth
                  error={errors.roleName ? true : false}
                        slotProps={{
                          input: {
                            startAdornment: (
                              <InputAdornment position="start">
                                <Label />
                              </InputAdornment>
                            ),
                          },
                        }}
                        {...register('roleName', {
                          required: {
                            value: true,
                            message:getMessage('E_TSCL_01_0006', ['Role Name']),
                          },
                          maxLength: {
                            value: 50,
                            message:getMessage('E_TSCL_01_0016', ['Role Name']),
                          },
                        })}
                />
                {errors.roleName && <p className="errorText">
                {errors.roleName.message}</p>}
              </div>
              <div>
                <TextField
                  id="txtRoleShName"
                  label= {labelText.roleShName}
                  variant="outlined"
                  fullWidth
                  error={errors.roleShName ? true : false}
                        slotProps={{
                          input: {
                            startAdornment: (
                              <InputAdornment position="start">
                                <Label />
                              </InputAdornment>
                            ),
                          },
                        }}
                        {...register('roleShName', {
                          required: {
                            value: true,
                            message: getMessage('E_TSCL_01_0006', 
                                  ['Role Short Name']),
                          },
                          maxLength: {
                            value: 50,
                            message: getMessage('E_TSCL_01_0016', 
                                  ['Role Short Name']),
                          },
                        })}
                />
                {errors.roleShName && <p className="errorText">
                {errors.roleShName.message}</p>}
              </div>
            </Box>
            <Box className="permission-box">
              <List sx={{m:3}}>
                {functionList.map((func) => {
                
                  const isSecondary = func.functionCode.length > 2;
                  const formattedCode = formatFunctionCode(func.functionCode);
                  return (
                    <ListItem sx={{p:0}} key={func.functionCode} style={{ 
                            paddingLeft: isSecondary ? '20px' : '0' }}> {}
                      <Checkbox 
                        sx={{p:0}}
                        edge="start" 
                        checked={checked.includes(parseInt(func.functionCode))} 
                        onChange={() => handleToggle(func.functionCode)}
                        tabIndex={-1} 
                        disableRipple 
                      />
                      <ListItemText 
                        primary={`${formattedCode} ${func.functionName}`} 
                      />
                    </ListItem>
                  );
                })}
              </List>
            </Box>
            <Box sx={{ display: 'flex', flexDirection: 'row-reverse', 
                    gap: 2, mr: 6, m:1 }}>
              <Button variant="outlined" color="info" size="large" 
                    onClick={cancelRegistration}>
                Cancel
              </Button>
              <Button variant="contained" color="primary" size="large" 
                    onClick={handleRegisterClick}>
                Register
              </Button>
            </Box>
          </Stack>
			  </Box>
      </FormControl>
      <DialogBox
        show={show}
        dialogMessage={dialogMessage}
        handleClose={handleClose}
        handleYes={handleYes}
        handleNo={() => setShow(false)}
        confirmation={confirmationMessage}
      />
      </div>
    </div>
  );
}
