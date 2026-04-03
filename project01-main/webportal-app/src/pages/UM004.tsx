/*
 * UM004.tsx
 * User Delete Page
 *
 * v 00.001 - 10/21/2024
 *
 * PIC: emonteverde
 */
import { FC, useEffect, useState } from 'react';
import PanelHeader from '../component/PanelHeader';
import { useNavigate } from 'react-router-dom';
import { Box, Button, FormControl, InputAdornment, TextField } from '@mui/material';
import { Email, GroupOutlined, PermIdentity, Person } from '@mui/icons-material';
import apiAxiosConfig from '../common/apiAxiosConfig';
import { useForm, SubmitHandler } from 'react-hook-form';
import { getMessage } from '../common/MessageUtil';
import { UsersItem } from '../types/DataType';
import '../style/main.css';
import { useSelector } from 'react-redux';
import { RootState } from '../redux/store';
import { labelText, routes } from '../common/appConstant';
import DialogComponent from '../component/DialogBox';


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

export const UserDelete: FC = () => {
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

	const [showDialog, setShowDialog] = useState(false);
	const [showConfirmDialog, setShowConfirmDialog] = useState(false);
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

	// Back button handler
	const handleBackClick = () => {
		reset();
		navigate(routes.UM001);
	};

	// Delete button handler
	const handleDelete: SubmitHandler<UsersItem> = (data) => {
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
				const response = await apiAxiosConfig.post('/deleteUser', formData, {
					headers: {
						'Content-Type': 'multipart/form-data',
					},
				});
				if (response.status === 200) {
					if (response.data.errorCode) {
						setDialogMessage(getMessage(response.data.errorCode, [response.data.errorItem]));
						setShowDialog(true);
					} else {
						setDialogMessage(getMessage('I_TSCL_01_0037'));
						setShowDialog(true);
					}
				}
				setIsLoading(false);
			} catch (error: any) {
				setDialogMessage(getMessage('E_TSCL_01_0033'));
				setShowDialog(true);
				setIsLoading(false);
			}
		};
		fetch();
	};

	// Initial fetch data
	useEffect(() => {
		setIsLoading(true);
		const fetch = async () => {
			try {
				const response = await apiAxiosConfig.post('/dispUM004', null, {
					params: {
						loggedInUsername: loggedInUsername,
						loggedInUserRole: loggedInUserRole,
						userId: userId,
					},
				});
				const res = response.data;
				setScreenTitle(res.screenTitle);
				setLstPosition(res.positionList);
				setLstRole(res.roleList);
				setLstDepartment(res.departmentList);
				setLstBusinessUnit(res.sectionList);
				setLstEmployeeStatus(res.userStatusList);

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
				setDialogMessage(getMessage('E_TSCL_01_0033', [getValues('username')]));
				setShowDialog(true);
				setIsLoading(false);
			}
		};
		fetch();
	}, [loggedInUsername, loggedInUserRole, userId, setValue, getValues]);

	return (
		<div>
			<PanelHeader showBackButton={true} onBackClick={handleBackClick} title={screenTitle} />

			<div className="webportal_screen">
				<FormControl fullWidth component="form" onSubmit={handleSubmit(handleDelete)}>
					<Box sx={{ display: 'flex', flexDirection: 'row', gap: 2, mx: 10 }}>
						<Box sx={{ display: 'flex', flexDirection: 'column', width: '25%', mt: 2, alignItems: 'center' }}>
							<div className="profile_picture">{imagePreview ? <img src={imagePreview} alt="Profile Preview" style={{ width: 300, height: 300 }} /> : <img src="defaultImage.jpg" alt="Profile" />}</div>
						</Box>

						<Box sx={{ display: 'flex', flexDirection: 'column', width: '100%' }}>
							<Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: 10, m: 2, width: '100%' }}>
								<div>
									<TextField
										id="txtFirstName"
										label={labelText.firstName}
										disabled
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
										{...register('firstName')}
									/>
									{errors.firstName && <p className="errorText">{errors.firstName.message}</p>}
								</div>
								<div>
									<TextField
										id="textLastName"
										label={labelText.lastName}
										disabled
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
										{...register('lastName')}
									/>
									{errors.lastName && <p className="errorText">{errors.lastName.message}</p>}
								</div>
								<TextField
									id="txtMiddleName"
									label={labelText.middleName}
									disabled
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
									{...register('middleName')}
								/>
							</Box>
							<Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: 10, m: 2, width: '100%' }}>
								<div>
									<TextField
										id="txtAssociateID"
										label={labelText.associateId}
										disabled
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
										{...register('associateId')}
									/>
									{errors.associateId && <p className="errorText">{errors.associateId.message}</p>}
								</div>
								<div>
									<TextField
										id="txtUsername"
										label={labelText.username}
										disabled
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
										{...register('username')}
									/>
									{errors.username && <p className="errorText">{errors.username.message}</p>}
								</div>
								<div>
									<TextField
										id="txtEmail"
										label={labelText.mailAddress}
										disabled
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
										{...register('email')}
									/>
									{errors.email && <p className="errorText">{errors.email.message}</p>}
								</div>
							</Box>
							<Box sx={{ display: 'grid', gridTemplateColumns: '1fr 1fr 1fr', gap: 10, m: 2, width: '100%' }}>
								<TextField
									id="lstEmployeeStatus"
									label={labelText.employeeStatus}
									disabled
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
									{...register('statusId')}>
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
									disabled
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
									{...register('positionId')}>
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
										disabled
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
										{...register('roleId')}>
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
									disabled
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
									{...register('departmentId')}>
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
									disabled
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
									{...register('businessUnitId')}>
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
						<Button variant="contained" color="primary" size="large" onClick={() => setShowConfirmDialog(true)}>
							{labelText.delete}
						</Button>
						{/* Confirm Modal For Register Button*/}
						<DialogComponent
							show={showConfirmDialog}
							dialogMessage={getMessage('E_TSCL_01_0012', [getValues('username')])}
							handleClose={() => setShowConfirmDialog(false)}
							confirmation={true}
							handleYes={() => {
								handleSubmit(handleDelete)();
								setShowConfirmDialog(false);
							}}
							handleNo={() => setShowConfirmDialog(false)}
						/>
						{/* Confirm Modal For Cancel Button*/}
						<DialogComponent show={showCancelDialog} dialogMessage={getMessage('I_TSCL_01_0025')} handleClose={() => setShowCancelDialog(false)} confirmation={true} handleYes={handleBackClick} handleNo={() => setShowCancelDialog(false)} />
					</Box>
				</FormControl>
			</div>
			{/* Message Modal */}
			<DialogComponent
				show={showDialog}
				dialogMessage={dialogMessage}
				handleClose={() => {
					setShowDialog(false);
					navigate(routes.UM001);
				}}
			/>
			{isLoading && (
				<div className="loading_page">
					{/* 3 dots animation */}
					<div className="loading_animation"></div>
				</div>
			)}
		</div>
	);
};
