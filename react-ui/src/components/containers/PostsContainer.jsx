import React from 'react';
import {PostListItem} from '../post/PostListItem';
import {canBeModified} from '../../utils/blogUtils';
import {AuthService} from '../../services/auth.service';
import {Link} from 'react-router-dom';

export const PostsContainer = ({blogId, authorUsername = null, posts = []}) => {
    const canAddPost = (username) => {
        return username &&
            AuthService.getCurrentUser() &&
            username === AuthService.getCurrentUser().username;
    };

    return (
        <>
            <div className="w-100">
                <div
                    className={'d-flex flex-row justify-content-between align-items-center'}>
                    <h2 className="text-dark">Posts</h2>
                    {authorUsername &&
                        canAddPost(authorUsername) && (
                            <Link
                                to={'posts/add'}
                                className="btn btn-success m-0 px-2 py-1 text-white h-25"
                            >
                                Add Post
                            </Link>
                        )
                    }
                </div>
                {posts && posts.length ? (
                    posts.map(
                        (post) => <PostListItem
                            key={post.id}
                            blogId={blogId}
                            post={post}
                            canBeModified={canBeModified(authorUsername)}
                        />,
                    )
                ) : (
                    <p className={'text-black-50'}>
                        No posts available.
                    </p>
                )}
            </div>
        </>
    );
};
