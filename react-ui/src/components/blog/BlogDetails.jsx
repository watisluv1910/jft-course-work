import {Link, useNavigate, useParams} from 'react-router-dom';
import React, {useEffect, useState} from 'react';
import {BlogService} from '../../services/blog.service';
import {canBeModified} from '../../utils/blogUtils';
import {Route, Routes} from 'react-router';
import {PostsContainer} from '../containers/PostsContainer';

export const BlogDetails = () => {
    const {id} = useParams();
    const navigate = useNavigate();
    const [error, setError] = useState(null);
    const [blogInfo, setBlogInfo] = useState({});

    useEffect(() => {
        BlogService
            .getBlog(id)
            .then(
                (result) => {
                    setBlogInfo(result);
                },
                (error) => {
                    setError(error);
                },
            );
    }, []);

    const handleDeleteBlog = () => {
        BlogService.deleteBlog(id).then(() => {
            navigate('/blogs');
        });
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
                            className={'card-header d-flex gap-3 bg-dark text-white justify-content-between'}>
                            <h1>{blogInfo.title}</h1>
                            {blogInfo.author &&
                                blogInfo.author.username &&
                                canBeModified(blogInfo.author.username) && (
                                    <div className={'d-flex ' +
                                        'justify-content-between ' +
                                        'align-items-center ' +
                                        'gap-3'
                                    }>
                                        <Link
                                            to={'/blogs/' + id + '/edit'}
                                            className="btn btn-primary m-0 px-2 py-1 text-white"
                                        >
                                            Edit
                                        </Link>
                                        <button
                                            className="btn btn-danger m-0 px-2 py-1"
                                            onClick={handleDeleteBlog}
                                        >
                                            Delete
                                        </button>
                                    </div>
                                )}
                        </div>
                        <div className="card-body">
                            <div className="mb-2">
                                {blogInfo.author && blogInfo.author.username && (
                                    <div
                                        className="d-flex align-items-center mb-2">
                                    <span className="fw-bold text-primary me-2">
                                        Author:
                                    </span>
                                        <span className="fw-medium">
                                        {blogInfo.author.username}
                                    </span>
                                    </div>
                                )}
                                <div className="d-flex align-items-center">
                                <span className="fw-bold text-primary me-2">
                                    Last Edited At:
                                </span>
                                    <span className="fw-medium">
                                    {
                                        new Date(blogInfo.lastEditDate)
                                            .toLocaleString()
                                    }
                                </span>
                                </div>
                            </div>
                            <div
                                className="fw-bold text-primary me-3">Description:
                            </div>
                            <p className="card-text">{blogInfo.description}</p>
                        </div>
                    </div>

                    <Routes>
                        <Route
                            exact path="/"
                            element={
                                <PostsContainer
                                    authorUsername={blogInfo.author?.username}
                                    blogId={blogInfo.id}
                                    posts={blogInfo.posts}
                                />
                            }
                        />
                    </Routes>
                </div>
            )}
        </>
    );
};
