import api from '../config/api';

const boardUrl = `/board/`;

const getAuthorBoard = () => {
    return api.get(boardUrl + 'author');
};

const getModeratorBoard = () => {
    return api.get(boardUrl + 'moderator');
};

const getAdminBoard = () => {
    return api.get(boardUrl + 'admin');
};

const deleteUser = (userId) => {
    return api.delete(boardUrl + `moderator/deleteUser/${userId}`);
};

export const BoardsService = {
    getAuthorBoard,
    getModeratorBoard,
    getAdminBoard,
    deleteUser
};
