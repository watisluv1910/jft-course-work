import {Link, useNavigate, useParams} from 'react-router-dom';
import React, {useEffect, useState} from 'react';
import {BlogService} from '../../services/blog.service';
import {AuthService} from '../../services/auth.service';

export const PostDetails = () => {
    const {blogId, postId} = useParams();
    const navigate = useNavigate();
    const [error, setError] = useState(null);
    const [postInfo, setPostInfo] = useState({});

    useEffect(() => {
        BlogService.getPost(blogId, postId)
            .then(
                (data) => {
                    setPostInfo(data);
                },
                (error) => {
                    setError(error);
                },
            );
    }, [blogId, postId]);

    const handleDeletePost = () => {
        BlogService.deletePost(blogId, postId)
            .then(() => {
                navigate(`/blogs/${blogId}`);
            })
            .catch((error) => {
                setError(error);
            });
    };

    const canBeModified = (username) => {
        return username &&
            AuthService.getCurrentUser() &&
            username === AuthService.getCurrentUser().username;
    };

    return (
        <>
            {error ? (
                <div>Error: {error.message}</div>
            ) : (
                <div
                    className="container d-flex flex-column align-content-center">
                    <div className="card m-0 mb-4 p-0 align-self-center">
                        <div
                            className="card-header d-flex gap-3 bg-dark text-white justify-content-between">
                            <h1>{postInfo.title}</h1>
                            {postInfo.author && postInfo.author.username && canBeModified(postInfo.author.username) && (
                                <div
                                    className="d-flex justify-content-between align-items-center gap-3">
                                    <Link
                                        to={'/blogs/' + blogId + '/posts/' + postId + '/edit'}
                                        className="btn btn-primary m-0 px-2 py-1 text-white"
                                    >
                                        Edit
                                    </Link>
                                    <button
                                        className="btn btn-danger m-0 px-2 py-1"
                                        onClick={handleDeletePost}
                                    >
                                        Delete
                                    </button>
                                </div>
                            )}
                        </div>
                        <div className="card-body">
                            <div className="mb-2">
                                {postInfo.author && postInfo.author.username && (
                                    <div
                                        className="d-flex align-items-center mb-2">
                                        <span
                                            className="fw-bold text-primary me-2">Author:</span>
                                        <span
                                            className="fw-medium">{postInfo.author.username}</span>
                                    </div>
                                )}
                                <div className="d-flex align-items-center">
                                    <span className="fw-bold text-primary me-2">Last Edited At:</span>
                                    <span className="fw-medium">
                                        {new Date(postInfo.lastEditDate).toLocaleString()}
                                    </span>
                                </div>
                                <div className="d-flex align-items-center">
                                    <span
                                        className="fw-bold text-primary me-2">Likes:</span>
                                    <span
                                        className="fw-medium">{postInfo.likedUsersId ? postInfo.likedUsersId.length : 0}</span>
                                </div>
                            </div>
                            <div
                                className="fw-bold text-primary me-3">Description:
                            </div>
                            <p className="card-text">{postInfo.description}</p>
                            <div
                                className="fw-bold text-primary me-3">Content:
                            </div>
                            <p className="card-text">{postInfo.content}</p>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
};
