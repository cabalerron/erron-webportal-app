const messageMap = new Map([
  ['E_TSCL_01_0001',	'Either Username and/or Password is incorrect.'],
  ['E_TSCL_01_0002',	'Password must contain atleast a lowercase letter, a capital letter, a number, and a special character.'],
  ['E_TSCL_01_0003',	'Your account was locked. Please contact administrator to unlock your profile.'],
  ['E_TSCL_01_0004',	'Password must be atleast 8 characters length.'],
  ['E_TSCL_01_0005',	'New password and Confirm password does not match.'],
  ['E_TSCL_01_0006',	'{0} is required.'],
  ['E_TSCL_01_0007',	'Maximum image size must be less than 1mb.'],
  ['E_TSCL_01_0008',	'Please upload valid image file. (JPEG, PNG only).'],
  ['E_TSCL_01_0009',	'{0} already exists.'],
  ['E_TSCL_01_0010',	'Are you sure you want to register {0}?'],
  ['E_TSCL_01_0011',	'Save changes to {0}?'],
  ['E_TSCL_01_0012',	'Are you sure you want to delete the selected items: {0}?'],
  ['E_TSCL_01_0013',	'Below {0} have been deleted. {0}'],
  ['E_TSCL_01_0014',	'{0} not found.'],
  ['E_TSCL_01_0015',	'Search result.'],
  ['E_TSCL_01_0016',	'Please input valid {0}.'],
  ['E_TSCL_01_0017',	'Kindly provide at least one search criterion.'],
  ['E_TSCL_01_0018',	'There is no updated data available.'],
  ['E_TSCL_01_0019',	'Input must be in single-byte alphanumeric characters.'],
  ['E_TSCL_01_0020',	'Unable to display the screen.'],
  ['E_TSCL_01_0021',	'Failed to register in database.'],
  ['I_TSCL_01_0022',	'Are you sure you want to log out?'],
  ['E_TSCL_01_0023',	'The data has been registered successfully.'],
  ['E_TSCL_01_0024',	'The data has been updated successfully.'],
  ['I_TSCL_01_0025',	'Would you like to cancel this transaction?'],
  ['E_TSCL_01_0026',	'The email address you enter is unregistered.'],
  ['I_TSCL_01_0027',	'Password reset link successfully sent.'],
  ['E_TSCL_01_0028',	'The number of characters in {0} is incorrect.'],
  ['I_TSCL_01_0029',	'No {0} available to display.'],
  ['I_TSCL_01_0030',	'You do not have permission to access this page.'],
  ['I_TSCL_01_0031',	'Old Password is incorrect.'],
  ['E_TSCL_01_0032',	'At least one {0} must be selected.'],
  ['E_TSCL_01_0033',	'Faild to delete this {0} in database.'],
  ['I_TSCL_01_0034',	'Are you sure you want to unlock this user?'],
  ['E_TSCL_01_0035',	'Password reset link is invalid or already expired.'],
  ['E_TSCL_01_0036',	'Password reset link email could not be sent.'],
  ['I_TSCL_01_0037',	'The data has been deleted successfully.'],
  ['E_TSCL_01_0038',	'There are active users with the role ID: {0}. The role cannot be deleted.'],  
  ['E_TSCL_01_0039',	'Failed to connect to database.'],  
  ['I_TSCL_01_0040',	  '{0} updated successfully.'],
  ['E_TSCL_01_0041',	'{0} must be before or equal to End Date'], 
  ['E_TSCL_01_0042',	'{0} must be after or equal to Start Date'], 
  ['I_TSCL_01_0043',	'Are you sure you want to update the selected items: {0} ? '],
  ['I_TSCL_01_0044',	'This account is already deleted'], 

]);

export const getMessage = (messageID: string, args?: (string | string[])[]): string => {
  let message = messageMap.get(messageID) || '';

  if (!args) {
    return message;
  }
  args.forEach((arg, i) => {
    const value = Array.isArray(arg) ? arg.join(', ') : arg;
    message = message.replace(new RegExp(`\\{${i}\\}`, 'g'), value);
  });

  return message;
};



