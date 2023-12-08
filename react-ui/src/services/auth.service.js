import api from '../config/api';

const authUrl = `/auth/`;

/**
 * Registers a new user by sending a POST request to the authentication API.
 *
 * @function
 * @param {string} username - The username of the user.
 * @param {string} userEmail - The email of the user.
 * @param {string} password - The password of the user.
 * @return {Promise<AxiosResponse<any>>} Axios promise
 * that returns the server's response.
 */
const register = (username, userEmail, password) => {
    return api.post(authUrl + 'register', {
        username,
        userEmail,
        password,
        roles: ['ROLE_USER', 'ROLE_MODERATOR'],
    }).catch((e) => console.error(e.message));
};

/**
 * Authenticates a user by sending a POST request to the authentication API.
 * Stores the user data in local storage if the response contains a user.
 *
 * @function
 * @param {string} username - The username of the user.
 * @param {string} password - The password of the user.
 * @return {Promise<object>} A promise that resolves
 * with the authenticated user's data.
 */
const login = (username, password) => {
    console.log('Login here');
    return api.post(authUrl + 'login', {
        username,
        password,
    }).then((response) => {
        if (response.data.user) {
            localStorage.setItem(
                'user',
                JSON.stringify(response.data.user),
            );
        }
        if (response.data.tokenExpiration) {
            localStorage.setItem(
                'token_expiration',
                JSON.stringify(response.data.tokenExpiration),
            );
        }
        return response.data;
    }).catch((e) => console.error(e.message));
};

/**
 * Logs out a user by sending a POST request to
 * the authentication API and removing the user data from local storage.
 *
 * @function
 * @return {Promise<object>} A promise that resolves
 * with the server's response data.
 */
const logout = () => {
    localStorage.clear();
    return api.post(authUrl + 'logout')
        .then((response) => response.data)
        .catch((e) => console.error(e.message));
};

const refreshAccessToken = () => {
    return api.post(authUrl + 'refresh-token')
        .then((res) => {
            const expiration = getTokenExpiration();
            if (expiration) {
                expiration['accessTokenExpiresAt'] =
                    res.data.accessTokenExpiresAt;

                localStorage.setItem(
                    'token_expiration',
                    JSON.stringify(expiration),
                );
            }
            console.log(res.data.message.content);
        })
        .catch((err) => console.error('Error refreshing access token', err));
};

/**
 * Gets the current user data from local storage.
 *
 * @function
 * @return {object|null} The user data or null
 * if no user data exists in local storage.
 */
const getCurrentUser = () => JSON.parse(localStorage.getItem('user'));

const getTokenExpiration = () =>
    JSON.parse(localStorage.getItem('token_expiration'));

export const AuthService = {
    register,
    login,
    logout,
    refreshAccessToken,
    getCurrentUser,
    getTokenExpiration,
};
