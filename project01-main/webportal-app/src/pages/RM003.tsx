/*
 * RM003
 * Used to edit a role
 *
 * v 00.001 - 11/06/2024
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
import { Function, RoleItem, Permission } from "../types/DataType"; 
import { RootState } from '../redux/store';
import { setFunction } from "../redux/FunctionReducer";
import PanelHeader from '../component/PanelHeader';
import DialogBox from "../component/DialogBox";
import { routes, labelText } from '../common/appConstant';
import { useForm, SubmitHandler } from 'react-hook-form';
import '../style/RM001.css';

export const RoleEdit: FC = () => {
  
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const loggedInUsername = 
        useSelector((state: RootState) => state.params.param01);
  const loggedInUserRole = 
        useSelector((state: RootState) => state.params.param02);
  const roleId = useSelector((state: RootState) => state.params.itemId);
  console.log(roleId)
  console.log(loggedInUsername)

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
  const [isEdit, setIsEdit] = useState(false);

  const handleEditClick = () => {
    setIsEdit(true);
    setDialogMessage(getMessage("E_TSCL_01_0011", [getValues('roleId')]));
    setConfirmationMessage(true);
    setShow(true);
  };

  const cancelEdit = () => {
    setIsEdit(false);
    setDialogMessage(getMessage("I_TSCL_01_0025"));
    setConfirmationMessage(true);
    setShow(true);
  };

  const handleClose = () => {
    if(isEdit){
      navigate(routes.RM001);
    }
    setShow(false);
  };

  const handleYes = () => {
    if(isEdit){
      handleSubmit(handleEdit)();
    }
    else if(!isEdit){
      navigate(routes.RM001);
    }
    setShow(false);
  };
  //Dialog Message End

  //Get role detail
  const functionList = 
        useSelector((state: RootState) => state.function.functionList);

  const handleFunctionListWithId = useCallback((content: Function[] = []) => {
    const functionListWithId = content.map((func: Function) => ({
      id: func.functionId,
      ...func,
    }));
    dispatch(setFunction({ functionList: functionListWithId }));
  }, [dispatch]);

  const [checked, setChecked] = useState<number[]>([]);

  const fetchFunction = useCallback(async () => {
    try {
      const response = await apiConfig.post('/role/initEdit', null, {
        params: {
          roleId: roleId,
        },
      });
      const data = response.data;
      console.log(roleId)
      console.log(data)
      setValue('roleId', data.roleDetail[0]?.roleId); 
      setValue('roleName', data.roleDetail[0]?.roleName); 
      setValue('roleShName', data.roleDetail[0]?.roleShName); 

      //Map permission by role_id
      const functionCodes = data.permissionList.flatMap(
        (permission: Permission) => permission.functionCode ? 
          permission.functionCode.map((code: string) => parseInt(code)) : []
      );

      setChecked(functionCodes);

      //Map functionList per module
      const primaryFunctionsMap = data.functionList.reduce(
        (map: Map<string, Function[]>, func: Function) => {
          if (func.moduleCode === null) {
              map.set(func.functionCode, []);
          } else {
              if (!map.has(func.moduleCode)) {
                  map.set(func.moduleCode, []);
              }
              map.get(func.moduleCode)?.push(func);
          }
          return map;
      }, new Map<string, Function[]>());

      //Check checkbox base on permission by role_id
      primaryFunctionsMap.forEach(
        (secondaryFunctions: Function[], primaryFunctionCode: string, ) => {

        const secondaryFunctionCodes = 
          secondaryFunctions.map((func) => func.functionCode);
  
        const filteredChecked = functionCodes.filter((code:number) => 
          code.toString().startsWith(primaryFunctionCode) 
        );

        const allSecondaryChecked = 
          secondaryFunctionCodes.every((secondaryFunc) => 
          filteredChecked.includes(parseInt(secondaryFunc))
        );

        if (allSecondaryChecked) {
            setChecked(prevChecked => [...prevChecked, 
              parseInt(primaryFunctionCode)]);
        }
      });

      handleFunctionListWithId(data.functionList);
    } catch (error) {
      console.error('Error fetching functions:', error);
    }
  }, [handleFunctionListWithId, setValue, roleId]);

  useEffect(() => {
    fetchFunction();
  }, [fetchFunction, dispatch]);

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

  //Format Function List as multilevel numbering 
  const formatFunctionCode = (code: string): string => {
    return code.split('').join('.'); 
  };

  //Edit role
  const handleEdit: SubmitHandler<RoleItem> = (data) => {
    if(checked.length === 0){
      setDialogMessage(getMessage("E_TSCL_01_0032", ["Permission"]));
      setConfirmationMessage(false); 
      setShow(true); 
      setIsEdit(false);
    }{
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
          const response = await apiConfig.post('/role/editRole', 
            formData, {
              headers: {
                'Content-Type': 'multipart/form-data',
              },
            });
          if (response.status === 200) {
            setShow(true);
            setConfirmationMessage(false);
            if(response.data.errCode === null){
              setDialogMessage(getMessage('I_TSCL_01_0040', [data.roleId]));
              setIsEdit(true);
              reset();
            }else{
              setDialogMessage(getMessage(response.data.errCode, 
                    [response.data.errItem]));
              setIsEdit(false);
            }
          }
        } 
        catch (error: any) {
          setDialogMessage(getMessage(error.response.data));
          setShow(true);
          setConfirmationMessage(false);
          setIsEdit(false);
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
        title="Role Edit"
      />
      <div className="webportal_screen">
      <FormControl fullWidth component="form" 
            onSubmit={handleSubmit(handleEdit)}>
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
                    onClick={cancelEdit}>
                Cancel
              </Button>
              <Button variant="contained" color="primary" size="large" 
                    onClick={handleEditClick}>
                Save
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