import React from 'react';
import {Link, useNavigate} from 'react-router-dom';
import {PostLikeButton} from './PostLikeButton';
import {BlogService} from '../../services/blog.service';
import {canBeModified} from '../../utils/blogUtils';

export const PostListItem = ({blogId, post}) => {
    const navigate = useNavigate();

    const handleDeletePost = () => {
        BlogService.deletePost(blogId, post.id).then(
            () => navigate(0),
        );
    };

    return (
        <div className="card p-1 mt-4 mb-4">
            <div className={'card-header ' +
                'd-flex ' +
                'justify-content-between ' +
                'align-content-center ' +
                'gap-3'
            }>
                <Link exact to={`/blogs/${blogId}/posts/${post.id}`}>
                    <h4 className="card-title">{post.title}</h4>
                </Link>
                {post.author &&
                    post.author.username &&
                    canBeModified(post.author.username) && (
                        <div className={'d-flex ' +
                            'justify-content-between ' +
                            'align-items-center ' +
                            'gap-3'
                        }>
                            <Link
                                to={'/blogs/' + blogId + '/posts/' + post.id + '/edit'}
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
                <div className="d-flex justify-content-between">
                    <div>
                        <span className="fw-bold">
                            Author:
                        </span> {post.author.username}
                    </div>
                </div>
                <div className="d-flex justify-content-between">
                    <div>
                        <span className="fw-bold">
                            Created:
                        </span> {new Date(post.creationDate).toLocaleString()}
                    </div>
                    <div className={'pe-1'}>
                        <PostLikeButton
                            blogId={blogId}
                            postId={post.id}
                            likedUsersId={post.likedUsersId}
                        />
                    </div>
                </div>
                <div>
                    <span className="fw-bold">
                        Categories:
                    </span> {post.categories.join(', ')}
                </div>
            </div>
        </div>
    );
};
