import api from '../config/api';

const getUserBoard = () => {
    return api.get('/test/user');
};

const getModeratorBoard = () => {
    return api.get('/test/moderator');
};

const getAdminBoard = () => {
    return api.get('/test/admin');
};

const deleteUser = (userId) => {
    return api.delete(`/test/moderator/deleteUser/${userId}`);
};

export const BoardsService = {
    getUserBoard,
    getModeratorBoard,
    getAdminBoard,
    deleteUser
};
