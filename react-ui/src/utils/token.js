import {AuthService} from '../services/auth.service';

const parseJwt = (token) => {
    try {
        return JSON.parse(atob(token.split('.')[1]));
    } catch (e) {
        console.error(e.message);
        return null;
    }
};

const isExpiredToken = (tokenType) => {
    try {
        const expiration = AuthService.getTokenExpiration();
        return expiration[tokenType + 'TokenExpiresAt'] < Date.now();
    } catch (e) {
        console.error(e.message);
        return false;
    }
};

const updateAccessToken = () => {
    AuthService.refreshAccessToken()
        .then((r) => console.log(r))
        .catch((_) => AuthService
            .logout()
            .then((res) => console.log(res.message)),
        );
};

export {parseJwt, isExpiredToken, updateAccessToken};
