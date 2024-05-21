import React, {useEffect, useState} from 'react';

import {BoardsService} from '../../services/boards.service';

export const BoardAdmin = () => {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        BoardsService.getAdminBoard().then(
            (response) => {
                setUsers(response.data);
            },
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setUsers(_content);
            },
        );
    }, []);

    const handleDeleteUser = (userId) => {
        BoardsService.deleteUser(userId)
            .then(() => {
                setUsers((prevContent) =>
                    prevContent.filter((user) => user.id !== userId));
            })
            .catch((e) => console.error(e));
    };

    const getRolesString = (roles) =>
        roles
            .sort()
            .toString()
            .replace(/,/g, ", ")
            .replace(/ROLE_/gi, " ");

    return (
        <div className="container-lg table-responsive-md">
            <table className={"table table-hover caption-top"}>
                <caption>
                    <h4>List of users</h4>
                </caption>
                <thead className={"table-header"}>
                    <tr key={"head"}>
                        <th scope={"col"}>User ID</th>
                        <th scope={"col"}>Username</th>
                        <th scope={"col"}>Email</th>
                        <th scope={"col"}>Roles</th>
                        <th scope={"col"}>Action</th>
                    </tr>
                </thead>
                <tbody className={"table-group-divider align-middle"}>
                    {users && users.map((user) => {
                        const {id, username, userEmail, roles} = user;
                        return (
                            <tr key={id}>
                                <th scope={"row"}>{id}</th>
                                <td>{username}</td>
                                <td>{userEmail}</td>
                                <td>{getRolesString(roles)}</td>
                                <td>
                                    <button
                                        className={"btn btn-danger btn-block"}
                                        onClick={() => handleDeleteUser(id)
                                    }>
                                        Delete
                                    </button>
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </div>
    );
};
