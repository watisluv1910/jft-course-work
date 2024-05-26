import React, {useState} from 'react';
import postLikeIconFilled from '../../assets/icons/post_like_icon_filled.png';
import postLikeIconOutline from '../../assets/icons/post_like_icon_outline.png';
import {AuthService} from '../../services/auth.service';
import {BlogService} from '../../services/blog.service';

export const PostLikeButton = ({blogId, postId, likedUsersId = []}) => {
    const [likedIds, setLikedIds] = useState(likedUsersId);
    console.log('update');
    const isLikedByCurrentUser = () => {
        if (likedIds === null ||
            likedIds.length === 0 ||
            AuthService.getCurrentUser() === null
        ) {
            return false;
        }

        return likedIds.some(
            (likedUserId) => likedUserId === AuthService.getCurrentUser().id,
        );
    };

    const getLikeIcon =
        () => isLikedByCurrentUser() ? postLikeIconFilled : postLikeIconOutline;

    const likePost = async () => {
        try {
            await BlogService.likePostFromBlog(blogId, postId)
                .then(({_, likedUsersId}) => {
                    console.log('Like:' + likedUsersId);
                    setLikedIds(likedUsersId);
                });
        } catch (error) {
            console.log('Error liking post', error);
        }
    };

    const dislikePost = async () => {
        try {
            await BlogService
                .dislikePostFromBlog(blogId, postId)
                .then(({_, likedUsersId}) => {
                    console.log('Dislike:' + likedUsersId);
                    setLikedIds(likedUsersId);
                });
        } catch (error) {
            console.error('Error disliking post: ', error);
        }
    };

    const handleLikeClick = () => {
        isLikedByCurrentUser() ? dislikePost() : likePost();
    };

    return (
        <div className={'row gap-2 px-2 justify-content-md-center'}>
            <div
                className={'col p-0 fs-5 text-center d-flex align-items-center'}>
                {likedIds.length}
            </div>
            <button
                className={'col p-0 border-0 bg-transparent d-flex align-items-center'}
                disabled={AuthService.getCurrentUser() === null}
                onClick={handleLikeClick}
            >
                <img
                    src={getLikeIcon()}
                    alt="Like icon"
                    style={{maxWidth: 24, aspectRatio: '1 / 1'}}
                />
            </button>
        </div>
    );
};
