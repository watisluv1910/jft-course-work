import React, {useEffect, useState} from 'react';
import Form from 'react-validation/build/form';
import Input from 'react-validation/build/input';
import Textarea from 'react-validation/build/textarea';
import {useNavigate, useParams} from 'react-router-dom';
import {BlogService} from '../../services/blog.service';
import {required} from '../utils/required';

export const PostEditor = ({isInEditMode}) => {
    const {blogId, postId} = useParams();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [content, setContent] = useState('');
    const [categories, setCategories] = useState('');
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        if (isInEditMode) {
            BlogService.getPost(blogId, postId)
                .then((post) => {
                    setTitle(post.title);
                    setDescription(post.description);
                    setContent(post.content);
                    setCategories(post.categories.join(', '));
                });
        }
    }, []);

    const handleSubmit = (event) => {
        event.preventDefault();
        const categoryList = categories.split(',').map((cat) => cat.trim());
        const upsertPostRequest = {
            title,
            description,
            content,
            categories: categoryList,
        };

        if (isInEditMode) {
            BlogService.editPost(blogId, postId, upsertPostRequest)
                .then((_) => {
                    navigate(`/blogs/${blogId}/posts/${postId}`);
                })
                .catch((error) => {
                    setError(error);
                });
        } else {
            BlogService.addPost(blogId, upsertPostRequest)
                .then(() => {
                    navigate(`/blogs/${blogId}`);
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
                        <h3>Edit post</h3> : <h3>Add New Post</h3>
                    }
                </div>
                <div className="card-body">
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
                        <div className="mb-2">
                            <label htmlFor="content"
                                   className="form-label mb-0">Content</label>
                            <Textarea
                                name="content"
                                className="form-control mt-0"
                                value={content}
                                onChange={(e) => setContent(e.target.value)}
                                validation={[required]}
                            />
                        </div>
                        <div className="mb-3">
                            <label htmlFor="categories"
                                   className="form-label mb-0">
                                Categories (comma separated)
                            </label>
                            <input
                                type="text"
                                id="categories"
                                className="form-control mt-0"
                                value={categories}
                                onChange={(e) => setCategories(e.target.value)}
                            />
                        </div>
                        <button
                            type="submit"
                            className="btn btn-success w-25"
                            style={{justifySelf: 'right'}}
                        >
                            {isInEditMode ? <span>Edit Post</span> :
                                <span>Add Post</span>}
                        </button>
                    </Form>
                </div>
            </div>
        </div>
    );
};
