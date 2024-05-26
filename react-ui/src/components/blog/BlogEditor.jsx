import React, {useEffect, useState} from 'react';
import Form from 'react-validation/build/form';
import Input from 'react-validation/build/input';
import Textarea from 'react-validation/build/textarea';
import {useNavigate, useParams} from 'react-router-dom';
import {BlogService} from '../../services/blog.service';
import {required} from '../utils/required';

export const BlogEditor = ({isInEditMode}) => {
    const {id} = useParams();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (isInEditMode) {
            BlogService.getBlog(id)
                .then((blog) => {
                    setTitle(blog.title);
                    setDescription(blog.description);
                });
        }
    }, []);

    const handleSubmit = (event) => {
        event.preventDefault();

        const upsertBlogRequest = {
            title,
            description,
        };

        if (isInEditMode) {
            BlogService.editBlog(id, upsertBlogRequest)
                .then((_) => {
                    navigate(`/blogs/${id}`);
                })
                .catch((error) => {
                    setError(error);
                });
        } else {
            BlogService.addBlog(upsertBlogRequest)
                .then(() => {
                    navigate(`/blogs`);
                })
                .catch((error) => {
                    setError(error);
                });
        }
    };

    return (
        <div className="container">
            <div className="card w-100 mx-auto my-auto">
                <div className="card-header bg-dark text-white">
                    {isInEditMode ?
                        <h3>Edit blog</h3> : <h3>Add New Blog</h3>
                    }
                </div>
                <div className="card-body pb-0">
                    {error && <div
                        className="alert alert-danger">{error.message}</div>}
                    <Form onSubmit={handleSubmit}>
                        <div className="mb-2">
                            <label htmlFor="title"
                                   className="form-label mb-0">Title</label>
                            <Input
                                type="text"
                                name="title"
                                className="form-control mt-0"
                                value={title}
                                onChange={(e) => setTitle(e.target.value)}
                                validation={[required]}
                            />
                        </div>
                        <div className="mb-2">
                            <label htmlFor="description"
                                   className="form-label mb-0">Description</label>
                            <Textarea
                                name="description"
                                className="form-control mt-0"
                                value={description}
                                onChange={(e) => setDescription(e.target.value)}
                                validation={[required]}
                            />
                        </div>
                        <button
                            type="submit"
                            className="btn btn-success w-25"
                            style={{justifySelf: 'right'}}
                        >
                            {isInEditMode ? <span>Edit Blog</span> :
                                <span>Add Blog</span>}
                        </button>
                    </Form>
                </div>
            </div>
        </div>
    );
};
