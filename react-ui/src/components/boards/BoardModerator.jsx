import React, {useEffect, useState} from 'react';

import {BoardsService} from '../../services/boards.service';

export const BoardModerator = () => {
    const [content, setContent] = useState([]);

    useEffect(() => {
        BoardsService.getModeratorBoard().then(
            ({data}) => setContent(data),
            (error) => {
                const _content =
                    (error.response &&
                        error.response.data &&
                        error.response.data.message) ||
                    error.message ||
                    error.toString();

                setContent(_content);
            },
        );
    }, []);

    const handleDeleteUser = (userId) => {
        BoardsService.deleteUser(userId).then(() => {
            setContent((prevContent) =>
                prevContent.filter((user) => user.id !== userId));
        }).catch((error) => {
            console.error('Error deleting user:', error);
        });
    };

    return (
        <div className="container">
            <header>
                <h3>All users</h3>
            </header>
            <div style={{paddingBlock: 20, margin: 'auto'}}>
                {content && content.map((user) => {
                    const {id, username, userEmail} = user;
                    return (
                        <div key={id}>
                            {username}: id {id} with email {userEmail}
                            <button onClick={() => handleDeleteUser(id)}>
                                Delete
                            </button>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};
