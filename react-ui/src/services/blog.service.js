import api from '../config/api';

const blogUrl = `/blog`;

const addBlog = function(upsertBlogRequest = {}) {
    return api.post(blogUrl + '/add', upsertBlogRequest)
        .catch((e) => console.error(e));
};

const editBlog = function(blogId, upsertBlogRequest = {}) {
    return api.put(blogUrl + '/' + blogId, upsertBlogRequest)
        .then(({data}) => data)
        .catch((e) => console.error(e));
};

const getBlogsByTitle = function(title = '') {
    return api.get(blogUrl + '?title=' + title).then(({data}) => data);
};

const getBlog = function(id) {
    return api.get(blogUrl + '/' + id).then(({data}) => data);
};

const deleteBlog = function(id) {
    return api.delete(blogUrl + '/' + id);
};

const getPost = function(blogId, postId) {
    return api.get(blogUrl + '/' + blogId + '/post/' + postId)
        .then(({data}) => data);
};

const addPost = function(blogId, upsertRequest) {
    return api.post(blogUrl + '/' + blogId + '/post/add', upsertRequest)
        .then(({data}) => data);
};

const editPost = function(blogId, postId, upsertRequest) {
    return api.put(blogUrl + '/' + blogId + '/post/' + postId, upsertRequest)
        .then(({data}) => data);
};

const deletePost = function(blogId, postId) {
    return api.delete(blogUrl + '/' + blogId + '/post/' + postId);
};

const likePostFromBlog = function(blogId, postId) {
    return api.post(blogUrl + '/' + blogId + '/post/' + postId + '/like')
        .then(({data}) => data);
};

const dislikePostFromBlog = function(blogId, postId) {
    return api.delete(blogUrl + '/' + blogId + '/post/' + postId + '/dislike')
        .then(({data}) => data);
};

export const BlogService = {
    addBlog,
    getBlog,
    getBlogsByTitle,
    editBlog,
    deleteBlog,
    addPost,
    getPost,
    editPost,
    deletePost,
    likePostFromBlog,
    dislikePostFromBlog,
};
