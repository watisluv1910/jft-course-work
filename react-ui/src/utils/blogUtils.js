import {AuthService} from '../services/auth.service';

export const canBeModified = (username) => {
    if (username &&
        AuthService.getCurrentUser() &&
        (username === AuthService.getCurrentUser().username ||
            AuthService.getCurrentUser().roles.includes('ROLE_MODERATOR'))
    ) {
        return true;
    }

    return false;
};

export const canBeAddedByCurrentUser = () => {
    return AuthService.getCurrentUser() &&
        AuthService.getCurrentUser().roles.includes('ROLE_USER');
};
