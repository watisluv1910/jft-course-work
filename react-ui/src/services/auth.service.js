import api from '../config/api';

api.interceptors.request.use((config) => {
    const controller = new AbortController();
    if (isRefreshTokenExpired()) {
        try {
            controller.abort();
            localStorage.clear();
            window.location.href = '/';
        } catch (error) {
            console.error('Error during logout:', error);
        }
    }
    return {
        ...config,
        signal: controller.signal,
    };
}, (error) => Promise.reject(error));

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
const register = function(
    username,
    userEmail,
    password,
) {
    return api.post(authUrl + 'register', {
        username,
        userEmail,
        password,
        roles: ['ROLE_USER', 'ROLE_MODERATOR', 'ROLE_ADMIN'],
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
const login = function(username, password) {
    return api.post(authUrl + 'login', {
        username, password,
    }).then((response) => {
        if (response.data.user) {
            localStorage.setItem(
                'user',
                JSON.stringify(response.data.user),
            );
        }
        if (response.data.refreshTokenExpirationDate) {
            localStorage.setItem(
                'refresh-token_expiration',
                JSON.stringify(response.data.refreshTokenExpirationDate),
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
const logout = async function() {
    try {
        await api.post(authUrl + 'logout');
    } catch (error) {
        console.error('Error during logout:', error);
    }
};

/**
 * Gets the current user data from local storage.
 *
 * @function
 * @return {object|null} The user data or null
 * if no user data exists in local storage.
 */
const getCurrentUser = function() {
    return JSON.parse(localStorage.getItem('user'));
};

const getRefreshTokenExpiration = function() {
    return JSON.parse(localStorage.getItem('refresh-token_expiration'));
};

const isRefreshTokenExpired = function() {
    if (localStorage.getItem('refresh-token_expiration') !== null) {
        return getRefreshTokenExpiration() < Date.now();
    }

    return false;
};

export const AuthService = {
    register,
    login,
    logout,
    getCurrentUser,
    getRefreshTokenExpiration,
    isRefreshTokenExpired,
};
