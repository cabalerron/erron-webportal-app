// Define routes
const routes: { [key: string]: string } = {
    // Landing Pages
    LOGIN: "/",
    LGN001: "/LGN001",

    // Home Pages
    HM001: "/HM001",

    // News Pages
    NM001: "/NM001",
    NM002: "/NM002",
    NM003: "/NM003",
    NM004: "/NM004",
    NM005: "/NM005",

    // User Pages
    UM001: "/UM001",
    UM002: "/UM002",
    UM003: "/UM003",
    UM004: "/UM004",
    UM005: "/UM005",

    // Role Pages
    RM001: "/RM001",
    RM002: "/RM002",
    RM003: "/RM003",
    RM004: "/RM004",

    // Password Recovery Pages
    PW001: "/PW001",
    PW002: "/PW002",
};

// Define column headers type
export type ColumnHeadersType = {
    newsId: string;
    title: string;
    content : string;
    createId: string;
    updateId: string;
    createDate: string;
    updateDate: string;
    startDate: string;
    endDate: string;
    image: string;

    // Login 
    accountId: string;
    password: string;
    forgotPassword: string;

    // Email Input
    mailAddress: string;
    sendRestLink: string;
	
	// Forgot Password
	oldPassword: string;
    newPassword: string;
    confirmPassword: string;

    //Role
    roleId: string;
    roleName: string;
    roleShName : string;
};

// Define column headers
const ColumnHeaders: ColumnHeadersType = {
    newsId: "News ID",
    title: "Title",
    content: "Content",
    createId: "Created By",
    createDate: "Created At",
    updateId: "Updated By",
    updateDate: "Updated At",
    startDate: "Start date",
    endDate: "End date",
    image: "Image",

    // Login
    accountId: "Account ID",
    password: "Password",
    forgotPassword: "Forgot Password?",
    // Email Input
    mailAddress: "Email Address",
    sendRestLink: "Send Reset Link",
	
	// Forgot Password
	oldPassword: "Old Password",
    newPassword: "New Password",
    confirmPassword: "Confirm Password",

    //Role
    roleId: "Role ID",
    roleName: "Role Name",
    roleShName: "Role Short Name"
};
 
// start NM002 news_content Rich text field
const richTextOptions = [
  [{ 'header': '1' }, { 'header': '2' }],                 // Header dropdown
  [{ 'list': 'ordered'}, { 'list': 'bullet' }],           // List options
  ['bold', 'italic', 'underline', 'strike'],              // Toggle styles
  [{ 'color': [] },],                                     // Text colors
  [{ 'align': [] }],                                      // Text align options
  ['clean']                                               // Remove formatting
];

const richTextModules = {
  toolbar: {
    container: richTextOptions,
  },
};

const newsMaxImageSize = 1024;

// end NM002 news_content Rich text field

// Define label text
const labelText: { [key: string]: string } = {
    // User Page
    firstName: "First Name",
    middleName: "Middle Name",
    lastName: "Last Name",
    associateId: "Associate ID",
    username: "Username",
    mailAddress: "Email",
    employeeStatus: "Employee Status",
    position: "Position",
    role: "Role",
    department: "Department",
    businessUnit: "Business Unit",
    changePassword: "Change Password",
    register: "Register",
    update: "Update",
    delete: "Delete",
    cancel: "Cancel",
    unlock: "Unlock",
  post: "Post",

    //News Page
    newsId: "News ID",
    createdBy: "Created By",
    display: "Display:",
    startDate: "Start Date",
    endDate: "End Date",
    title: "Title",
    contents: "Contents",
    attachment: "Attachments",
    uploadFile: "Uploaded File:",
    dragAndDrop: "Drag and drop files here or click to browse",
    useThisImage: "Use this Image",


};


const paginationDefaults = {
  page: 0,
  size: 10,
  totalItems: 0,
};

const DATE_FORMATS = {
  defaultFormat: 'YYYY-MM-DD',
  emptyFormat: '',
}


// Exporting constants
export { routes, ColumnHeaders, richTextModules, labelText, paginationDefaults, DATE_FORMATS, newsMaxImageSize };
