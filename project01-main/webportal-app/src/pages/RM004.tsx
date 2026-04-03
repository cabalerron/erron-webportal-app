/*
 * RM004
 * Used to deleting a role
 *
 * v 00.001 - 10/31/2024
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

export const RoleDelete: FC = () => {
  
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const loggedInUsername = 
        useSelector((state: RootState) => state.params.param01);
  const loggedInUserRole = 
        useSelector((state: RootState) => state.params.param02);
        const roleId = useSelector((state: RootState) => state.params.itemId);

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
  const [isDelete, setIsDelete] = useState(false);

  const handleDeleteClick = () => {
    setIsDelete(true);
    setDialogMessage(getMessage("E_TSCL_01_0012", [getValues('roleId')]));
    setConfirmationMessage(true);
    setShow(true);
  };

  const cancelDelete = () => {
    setIsDelete(false);
    setDialogMessage(getMessage("I_TSCL_01_0025"));
    setConfirmationMessage(true);
    setShow(true);
  };

  const handleClose = () => {
    if(isDelete){
      navigate(routes.RM001);
    }
    setShow(false);
  };

  const handleYes = () => {
    if(isDelete){
      handleSubmit(handleDelete)();
    }
    else if(!isDelete){
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
      const response = await apiConfig.post('/role/initDelete', null, {
        params: {
          roleId: roleId,
        },
      });
      const data = response.data;
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

  //Format Function List as multilevel numbering 
  const formatFunctionCode = (code: string): string => {
    return code.split('').join('.'); 
  };

  //Delete role
  const handleDelete: SubmitHandler<RoleItem> = (data) => {
    const formData = new FormData();
      formData.append('loggedInUsername', loggedInUsername);
      formData.append('loggedInUserRole', loggedInUserRole);
      formData.append('roleId', data.roleId);

      const fetch = async () => {
        try {
          const response = await apiConfig.post('/role/deleteRole', 
            formData, {
              headers: {
                'Content-Type': 'multipart/form-data',
              },
            });
          if (response.status === 200) {
            setShow(true);
            setConfirmationMessage(false);
            if(response.data.errCode === null){
              setDialogMessage(getMessage('I_TSCL_01_0037'));
              setIsDelete(true);
              reset();
            }else{
              setDialogMessage(getMessage(response.data.errCode, 
                    [response.data.errItem]));
              setIsDelete(false);
            }
          }
        } 
        catch (error: any) {
          setDialogMessage(getMessage(error.response.data));
          setShow(true);
          setConfirmationMessage(false);
          setIsDelete(false);
        }
      };
		  fetch();
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
            onSubmit={handleSubmit(handleDelete)}>
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
                  {...register('roleId')}
                />
              </div>
              <div>
                <TextField
                  id="txtRoleName"
                  label= {labelText.roleName}
                  variant="outlined"
                  fullWidth
                  disabled
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
                        {...register('roleName')}
                />
              </div>
              <div>
                <TextField
                  id="txtRoleShName"
                  label= {labelText.roleShName}
                  variant="outlined"
                  fullWidth
                  disabled
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
                        {...register('roleShName')}
                />
              </div>
            </Box>
            <Box className="permission-box">
              <List sx={{m:3}}>
                {
                  functionList.map((func) => {
                  const isSecondary = func.functionCode.length > 2;
                  const formattedCode = formatFunctionCode(func.functionCode);
                  return (
                    <ListItem sx={{p:0}} key={func.functionCode} style={{ 
                            paddingLeft: isSecondary ? '20px' : '0' }}> {}
                      <Checkbox 
                        sx={{p:0}}
                        edge="start" 
                        checked={checked.includes(parseInt(func.functionCode))} 
                        tabIndex={-1} 
                        disableRipple 
                        disabled 
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
                    onClick={cancelDelete}>
                Cancel
              </Button>
              <Button variant="contained" color="primary" size="large" 
                    onClick={handleDeleteClick}>
                Delete
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
