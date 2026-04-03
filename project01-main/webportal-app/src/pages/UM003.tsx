/*
 * UM003.tsx
 * User Edit Page
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */
import { FC, useEffect, useState } from 'react';
import PanelHeader from '../component/PanelHeader';
import { useNavigate } from 'react-router-dom';
import { Box, Button, FormControl, InputAdornment, TextField } from '@mui/material';
import { Email, GroupOutlined, PermIdentity, Person, PhotoCameraOutlined } from '@mui/icons-material';
import apiAxiosConfig from '../common/apiAxiosConfig';
import { useForm, SubmitHandler, set } from 'react-hook-form';
import { getMessage } from '../common/MessageUtil';
import { UsersItem } from '../types/DataType';
import '../style/main.css';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import { labelText, routes } from '../common/appConstant';
import DialogComponent from '../component/DialogBox';
import { useDispatch } from 'react-redux';
import { setItemId, setParams } from '../redux/ParamUtilReducer';

const ADMIN_ROLE = '1';

type Position = {
	positionId: number;
	positionName: string;
};

type Role = {
	roleId: number;
	roleName: string;
};

type Department = {
	departmentId: number;
	departmentName: string;
};

type BusinessUnit = {
	sectionId: number;
	sectionName: string;
};

type EmployeeStatus = {
	statusId: number;
	statusName: string;
};

export const UserEdit: FC = () => {

	const loggedInUsername = useSelector((state: RootState) => state.params.param01);
	const loggedInUserRole = useSelector((state: RootState) => state.params.param02);
	const userId = useSelector((state: RootState) => state.params.itemId);

	const [isLoading, setIsLoading] = useState<Boolean | null>(null);
	const [screenTitle, setScreenTitle] = useState<string>('');
	const [lstPosition, setLstPosition] = useState<Position[]>();
	const [lstRole, setLstRole] = useState<Role[]>();
	const [lstDepartment, setLstDepartment] = useState<Department[]>();
	const [lstBusinessUnit, setLstBusinessUnit] = useState<BusinessUnit[]>();
	const [lstEmployeeStatus, setLstEmployeeStatus] = useState<EmployeeStatus[]>();
	const [imagePreview, setImagePreview] = useState<string | null>(null); // Store image preview URL
	const [isUserLocked, setIsUserLocked] = useState<number>(0);

	const [showDialog, setShowDialog] = useState(false);
	const [showUpdateDialog, setShowUpdateDialog] = useState(false);
	const [showUnlockDialog, setShowUnlockDialog] = useState(false);
	const [showCancelDialog, setShowCancelDialog] = useState(false);
	const [dialogMessage, setDialogMessage] = useState('');

	const {
		register,
		handleSubmit,
		setValue,
		getValues,
		reset,
		formState: { errors },
	} = useForm<UsersItem>();

	const navigate = useNavigate();
	const dispatch = useDispatch();

	// Handle back button click
	const handleBackClick = () => {
		reset();
		navigate(routes.UM001);
	};

	// Handle change password button click
	const handleChangePasswordClick = () => {
		dispatch(
			setParams({
				param01: loggedInUsername,
				param02: loggedInUserRole,
			})
		);
		dispatch(setItemId(userId));
		navigate('/UM003');
		navigate(routes.UM005);
	};

	// Handle unlock button click
	const handleUnlockClick = () => {
		setIsLoading(true);
		setShowUnlockDialog(false);
		const formData = new FormData();
		formData.append('loggedInUsername', loggedInUsername);
		formData.append('loggedInUserRole', loggedInUserRole);
		formData.append('userId', userId?.toString() || '');

		const fetch = async () => {
			try {
				const response = await apiAxiosConfig.post('/unlockUser', formData, {
					headers: {
						'Content-Type': 'form-data',
					},
				});

				if (response.status === 200) {
					if (response.data.errorCode) {
						setDialogMessage(getMessage(response.data.errorCode, [response.data.errorItem]));
						setShowDialog(true);
					} else {
						setIsUserLocked(0);
						setDialogMessage(getMessage('E_TSCL_01_0024'));
						setShowDialog(true);
					}
				}
				setIsLoading(false);
			} catch (error: any) {
				setDialogMessage(getMessage('E_TSCL_01_0021'));
				setShowDialog(true);
				setIsLoading(false);
			}
		};
		fetch();
	};

	// Handle update button click
	const handleUpdate: SubmitHandler<UsersItem> = (data) => {
		setIsLoading(true);
		const formData = new FormData();
		formData.append('loggedInUsername', loggedInUsername);
		formData.append('loggedInUserRole', loggedInUserRole);
		formData.append('userId', userId?.toString() || '');
		formData.append('associateId', data.associateId);
		formData.append('firstName', data.firstName);
		formData.append('middleName', data.middleName);
		formData.append('lastName', data.lastName);
		formData.append('positionId', data.positionId.toString());
		formData.append('roleId', data.roleId.toString());
		formData.append('email', data.email);
		formData.append('departmentId', data.departmentId.toString());
		formData.append('sectionId', data.businessUnitId.toString());
		formData.append('username', data.username);
		formData.append('userStatusId', data.statusId.toString());

		// If a file is selected, append it to FormData
		if (data.imageFile) {
			formData.append('imageFile', data.imageFile);
		}

		const fetch = async () => {
			try {
				const response = await apiAxiosConfig.post('/updateUser', formData, {
					headers: {
						'Content-Type': 'multipart/form-data',
					},
				});

				if (response.status === 200) {
					if (response.data.errorCode) {
						setDialogMessage(getMessage(response.data.errorCode, [response.data.errorItem]));
						setShowDialog(true);
					} else {
						setDialogMessage(getMessage('E_TSCL_01_0024'));
						setShowDialog(true);
					}
				}
				setIsLoading(false);
			} catch (error: any) {
				setDialogMessage(getMessage('E_TSCL_01_0021'));
				setShowDialog(true);
				setIsLoading(false);
			}
		};
		fetch();
	};

	// Initial fetch to get user details
	useEffect(() => {
		setIsLoading(true);
		const fetch = async () => {
			try {
				const response = await apiAxiosConfig.post('/dispUM003', null, {
					params: {
						loggedInUsername: loggedInUsername, // TODO: replace with session value
						loggedInUserRole: loggedInUserRole, // TODO: replace with session value
						userId: userId, // TODO: replace with value from store
					},
				});
				const res = response.data;
				setScreenTitle(res.screenTitle);
				setLstPosition(res.positionList);
				setLstRole(res.roleList);
				setLstDepartment(res.departmentList);
				setLstBusinessUnit(res.sectionList);
				setLstEmployeeStatus(res.userStatusList);
				setIsUserLocked(res.lockFlag);

				setImagePreview(res.imgUserPhoto);
				setValue('firstName', res.firstName);
				setValue('middleName', res.middleName);
				setValue('lastName', res.lastName);
				setValue('associateId', res.associateId);
				setValue('username', res.username);
				setValue('email', res.email);
				setValue('positionId', res.positionId);
				setValue('roleId', res.roleId);
				setValue('departmentId', res.departmentId);
				setValue('businessUnitId', res.sectionId);
				setValue('statusId', res.statusId);
				setIsLoading(false);
			} catch (error: any) {
				setDialogMessage(getMessage('E_TSCL_01_0020'));
				setShowDialog(true);
				setIsLoading(false);
			}
		};
		fetch();
	}, [loggedInUsername, loggedInUserRole, userId, setValue]);

	// Handle file input change and set the image preview
	const handleImageChange = (e: any) => {
		const file = e.target.files?.[0];
		if (file) {
			// validate file size: 1MB, file type: JPG, JPEG, PNG
			if (file.size > 1024 * 1024) {
				setDialogMessage(getMessage('E_TSCL_01_0007'));
				setShowDialog(true);
				return;
			}
			if (!['image/jpeg', 'image/png'].includes(file.type)) {
				setDialogMessage(getMessage('E_TSCL_01_0008'));
				setShowDialog(true);
				return;
			}
			setValue('imageFile', file); // Set the file in the form data
			setImagePreview(URL.createObjectURL(file)); // Set the image preview URL
		}
	};

	return (
		<div>
			<PanelHeader showBackButton={true} onBackClick={handleBackClick} title={screenTitle} />

			<div className="webportal_screen">
				<FormControl fullWidth component="form" onSubmit={handleSubmit(handleUpdate)}>
					<Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, mx: 10 }}>
						<Box sx={{ display: 'flex', flexDirection: 'column', width: '25%', mt: 2, alignItems: 'center' }}>
							<div className="profile_picture">{imagePreview ? <img src={imagePreview} alt="Profile Preview" style={{ width: 300, height: 300 }} /> : <img src="defaultImage.jpg" alt="Profile" />}</div>
							<Button
								// variant="contained"
								size="small"
								sx={{
									color: '#01579b',
									backgroundColor: 'rgb(210, 210, 210, 0.5)',
									borderRadius: '100%',
									padding: '5px 10px',
									cursor: 'pointer',
									minWidth: '50px',
									height: '50px',
									position: 'relative',
									marginTop: '-70px',
									marginLeft: '220px',
									boxShadow: '0px 2px 4px rgba(0,0,0,0.2)',
								}}
								component="label" // Makes the button work as the label for the file input
								className="upload_button">
								<PhotoCameraOutlined />
								<input type="file" hidden accept="image/jpeg, image/png" {...register('imageFile')} onChange={handleImageChange} />
							</Button>
							<Button variant="text" color="info" size="large" sx={{ mt: 4 }} onClick={handleChangePasswordClick}>
								<span style={{ textDecoration: 'underline' }}>Change Password</span>
							</Button>
						</Box>

						<Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
							<Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: 10, m: 2, width: '100%' }}>
								<div>
									<TextField
										id="txtFirstName"
										label={labelText.firstName}
										disabled={loggedInUserRole !== ADMIN_ROLE}
										variant="outlined"
										fullWidth
										error={errors.firstName ? true : false}
										slotProps={{
											input: {
												startAdornment: (
													<InputAdornment position="start">
														<Person />
													</InputAdornment>
												),
											},
										}}
										{...register('firstName', {
											required: {
												value: true,
												message: getMessage('E_TSCL_01_0006', ['First Name']),
											},
											maxLength: {
												value: 50,
												message: getMessage('E_TSCL_01_0016', ['First Name']),
											},
										})}
									/>
									{errors.firstName && <p className="errorText">{errors.firstName.message}</p>}
								</div>
								<div>
									<TextField
										id="textLastName"
										label={labelText.lastName}
										disabled={loggedInUserRole !== ADMIN_ROLE}
										variant="outlined"
										fullWidth
										error={errors.lastName ? true : false}
										slotProps={{
											input: {
												startAdornment: (
													<InputAdornment position="start">
														<Person />
													</InputAdornment>
												),
											},
										}}
										{...register('lastName', {
											required: {
												value: true,
												message: getMessage('E_TSCL_01_0006', ['Last Name']),
											},
											maxLength: {
												value: 50,
												message: getMessage('E_TSCL_01_0016', ['Last Name']),
											},
										})}
									/>
									{errors.lastName && <p className="errorText">{errors.lastName.message}</p>}
								</div>
								<TextField
									id="txtMiddleName"
									label={labelText.middleName}
									disabled={loggedInUserRole !== ADMIN_ROLE}
									variant="outlined"
									fullWidth
									slotProps={{
										input: {
											startAdornment: (
												<InputAdornment position="start">
													<Person />
												</InputAdornment>
											),
										},
									}}
									{...register('middleName', {
										maxLength: {
											value: 50,
											message: getMessage('E_TSCL_01_0016', ['Middle Name']),
										},
									})}
								/>
							</Box>
							<Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: 10, m: 2, width: '100%' }}>
								<div>
									<TextField
										id="txtAssociateID"
										label={labelText.associateId}
										disabled={loggedInUserRole !== ADMIN_ROLE}
										variant="outlined"
										defaultValue={getValues('associateId')}
										fullWidth
										error={errors.associateId ? true : false}
										slotProps={{
											input: {
												startAdornment: (
													<InputAdornment position="start">
														<PermIdentity />
													</InputAdornment>
												),
											},
										}}
										{...register('associateId', {
											required: {
												value: true,
												message: getMessage('E_TSCL_01_0006', ['Associate ID']),
											},
											maxLength: {
												value: 50,
												message: getMessage('E_TSCL_01_0016', ['Associate ID']),
											},
										})}
									/>
									{errors.associateId && <p className="errorText">{errors.associateId.message}</p>}
								</div>
								<div>
									<TextField
										id="txtUsername"
										label={labelText.username}
										disabled={loggedInUserRole !== ADMIN_ROLE}
										variant="outlined"
										fullWidth
										error={errors.username ? true : false}
										slotProps={{
											input: {
												startAdornment: (
													<InputAdornment position="start">
														<Person />
													</InputAdornment>
												),
											},
										}}
										{...register('username', {
											required: {
												value: true,
												message: getMessage('E_TSCL_01_0006', ['Username']),
											},
											maxLength: {
												value: 50,
												message: getMessage('E_TSCL_01_0016', ['Username']),
											},
										})}
									/>
									{errors.username && <p className="errorText">{errors.username.message}</p>}
								</div>
								<div>
									<TextField
										id="txtEmail"
										label={labelText.mailAddress}
										disabled={loggedInUserRole !== ADMIN_ROLE}
										variant="outlined"
										fullWidth
										error={errors.email ? true : false}
										slotProps={{
											input: {
												startAdornment: (
													<InputAdornment position="start">
														<Email />
													</InputAdornment>
												),
											},
										}}
										{...register('email', {
											required: {
												value: true,
												message: getMessage('E_TSCL_01_0006', ['Email']),
											},
											pattern: {
												value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/, // TODO: replace with actual email regex
												message: getMessage('E_TSCL_01_0016', ['Email']),
											},
											maxLength: {
												value: 100,
												message: getMessage('E_TSCL_01_0016', ['Email']),
											},
										})}
									/>
									{errors.email && <p className="errorText">{errors.email.message}</p>}
								</div>
							</Box>
							<Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: 10, m: 2, width: '100%' }}>
								<TextField
									id="lstEmployeeStatus"
									label={labelText.employeeStatus}
									disabled={loggedInUserRole !== ADMIN_ROLE}
									variant="outlined"
									fullWidth
									select
									slotProps={{
										select: {
											native: true,
										},
										input: {
											startAdornment: (
												<InputAdornment position="start">
													<GroupOutlined />
												</InputAdornment>
											),
										},
									}}
									{...register('statusId', {
										maxLength: {
											value: 20,
											message: getMessage('E_TSCL_01_0016', ['Employee Status']),
										},
									})}>
									<option value="" selected></option>
									{lstEmployeeStatus?.map((item) => (
										<option value={item.statusId} selected={item.statusId === getValues('statusId')}>
											{item.statusName}
										</option>
									))}
								</TextField>
								<TextField
									id="lstPosition"
									label={labelText.position}
									disabled={loggedInUserRole !== ADMIN_ROLE}
									variant="outlined"
									select
									slotProps={{
										select: {
											native: true,
										},
										input: {
											startAdornment: (
												<InputAdornment position="start">
													<GroupOutlined />
												</InputAdornment>
											),
										},
									}}
									{...register('positionId', {
										maxLength: {
											value: 100,
											message: getMessage('E_TSCL_01_0016', ['Position']),
										},
									})}>
									<option value="" selected></option>
									{lstPosition?.map((item) => (
										<option value={item.positionId} selected={item.positionId === getValues('positionId')}>
											{item.positionName}
										</option>
									))}
								</TextField>
								<div>
									<TextField
										id="lstRole"
										label={labelText.role}
										disabled={loggedInUserRole !== ADMIN_ROLE}
										variant="outlined"
										select
										fullWidth
										error={errors.roleId ? true : false}
										slotProps={{
											select: {
												native: true,
											},
											input: {
												startAdornment: <InputAdornment position="start">{/* add icon here if there is any */}</InputAdornment>,
											},
										}}
										{...register('roleId', {
											required: {
												value: true,
												message: getMessage('E_TSCL_01_0006', ['Role']),
											},
										})}>
										{lstRole?.map((item) => (
											// if roleId is 2, set it as default value and selected
											<option value={item.roleId} selected={item.roleId === getValues('roleId')}>
												{item.roleName}
											</option>
										))}
									</TextField>
									{errors.roleId && <p className="errorText">{errors.roleId.message}</p>}
								</div>
							</Box>

							<Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: 10, m: 2, width: '100%' }}>
								<TextField
									id="lstDepartment"
									label={labelText.department}
									disabled={loggedInUserRole !== ADMIN_ROLE}
									variant="outlined"
									select
									slotProps={{
										select: {
											native: true,
										},
										input: {
											startAdornment: (
												<InputAdornment position="start">
													<GroupOutlined />
												</InputAdornment>
											),
										},
									}}
									{...register('departmentId', {
										maxLength: {
											value: 100,
											message: getMessage('E_TSCL_01_0016', ['Department']),
										},
									})}>
									<option value="" selected></option>
									{lstDepartment?.map((item) => (
										<option value={item.departmentId} selected={item.departmentId === getValues('departmentId')}>
											{item.departmentName}
										</option>
									))}
								</TextField>

								<TextField
									id="lstBusinessUnit"
									label={labelText.businessUnit}
									disabled={loggedInUserRole !== ADMIN_ROLE}
									variant="outlined"
									select
									slotProps={{
										select: {
											native: true,
										},
										input: {
											startAdornment: <InputAdornment position="start">{/* add icon here if there is any */}</InputAdornment>,
										},
									}}
									{...register('businessUnitId', {
										maxLength: {
											value: 100,
											message: getMessage('E_TSCL_01_0016', ['Business Unit']),
										},
									})}>
									<option value="" selected></option>
									{lstBusinessUnit?.map((item) => (
										<option value={item.sectionId} selected={item.sectionId === getValues('businessUnitId')}>
											{item.sectionName}
										</option>
									))}
								</TextField>
							</Box>
						</Box>
					</Box>
					{/* button on very bottom right */}
					<Box sx={{ display: 'flex', flexDirection: 'row-reverse', gap: 2, mr: 6 }}>
						<Button variant="outlined" color="info" size="large" onClick={() => setShowCancelDialog(true)}>
							{labelText.cancel}
						</Button>
						{isUserLocked === 1 && (
							<Button variant="contained" color="success" size="large" onClick={() => setShowUnlockDialog(true)}>
								{labelText.unlock}
							</Button>
						)}
						<Button variant="contained" color="primary" size="large" onClick={() => setShowUpdateDialog(true)}>
							{labelText.update}
						</Button>
						{/* Confirm Modal For Update Button*/}
						<DialogComponent
							show={showUpdateDialog}
							dialogMessage={getMessage('E_TSCL_01_0011', [getValues('username')])}
							handleClose={() => setShowUpdateDialog(false)}
							confirmation={true}
							handleYes={() => {
								handleSubmit(handleUpdate)();
								setShowUpdateDialog(false);
							}}
							handleNo={() => setShowUpdateDialog(false)}
						/>
						{/* Confirm Modal For Unlock Button */}
						<DialogComponent show={showUnlockDialog} dialogMessage={getMessage('I_TSCL_01_0034')} handleClose={() => setShowUnlockDialog(false)} confirmation={true} handleYes={handleUnlockClick} handleNo={() => setShowUnlockDialog(false)} />
						{/* Confirm Modal For Cancel Button*/}
						<DialogComponent show={showCancelDialog} dialogMessage={getMessage('I_TSCL_01_0025')} handleClose={() => setShowCancelDialog(false)} confirmation={true} handleYes={handleBackClick} handleNo={() => setShowCancelDialog(false)} />
					</Box>
				</FormControl>
			</div>
			{/* Message Modal */}
			<DialogComponent show={showDialog} dialogMessage={dialogMessage} handleClose={() => setShowDialog(false)} />
			{/* Loading Animation */}
			{isLoading && (
				<div className="loading_page">
					{/* 3 dots animation */}
					<div className="loading_animation"></div>
				</div>
			)}
		</div>
	);
};
