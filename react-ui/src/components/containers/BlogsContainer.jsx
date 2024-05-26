import React, {useEffect, useRef, useState} from 'react';
import {
    StoriesContainerWrapper,
} from '../../assets/styles/StoriesContainerStyles';
import {BlogService} from '../../services/blog.service';
import {BlogListItem} from '../blog/BlogListItem';
import Form from 'react-validation/build/form';
import Input from 'react-validation/build/input';
import CheckButton from 'react-validation/build/button';
import {Link} from 'react-router-dom';
import {canBeAddedByCurrentUser} from '../../utils/blogUtils';

export const BlogsContainer = () => {
    const [error, setError] = useState(null);
    const [blogsInfoBrief, setBlogsInfoBrief] = useState([]);

    const form = useRef(null);
    const checkBtn = useRef(null);
    const [query, setQuery] = useState('');

    useEffect(() => {
        BlogService
            .getBlogsByTitle()
            .then(
                (result) => {
                    setBlogsInfoBrief(result);
                },
                (error) => {
                    setError(error);
                },
            );
    }, []);

    const handleSubmit = function(e) {
        e.preventDefault();

        setError(null);

        if (checkBtn.current.context._errors.length === 0) {
            BlogService
                .getBlogsByTitle(query)
                .then(
                    (result) => {
                        setBlogsInfoBrief(result);
                    },
                    (error) => {
                        setBlogsInfoBrief([]);
                        setError(error);
                    },
                );
        }
    };

    const onChangeQuery = (e) => {
        setQuery(e.target.value);
    };

    return <>
        <div className={'w-100'}>
            <Form
                onSubmit={handleSubmit}
                ref={form}
            >
                <div
                    className={'d-flex ' +
                        'justify-content-center ' +
                        'align-items-center ' +
                        'gap-3'
                    }>
                    <div className="form-group mb-0 w-50">
                        <Input className="form-control mr-sm-2" type="search"
                               placeholder="Place blog title here..."
                               aria-label="Search"
                               value={query}
                               onChange={onChangeQuery}
                        />
                    </div>
                    <button className="btn btn-outline-primary my-2 my-sm-0"
                            type="submit"
                    >
                        Search
                    </button>
                    {canBeAddedByCurrentUser() && (
                        <button className="btn btn-success my-2 my-sm-0">
                            <Link
                                to={'/blogs/add'}
                                className={'text-white'}
                            >
                                Add Blog
                            </Link>
                        </button>
                    )}
                </div>
                <CheckButton style={{display: 'none'}} ref={checkBtn}/>
            </Form>
        </div>

        <StoriesContainerWrapper data-test-id="blogs-container">
            {error && (
                <div>Error: {error.message}</div>
            )}

            {
                blogsInfoBrief &&
                blogsInfoBrief.length > 0 &&
                blogsInfoBrief.map(
                    (blog) => <BlogListItem key={blog.id} blog={blog}/>,
                )
            }
        </StoriesContainerWrapper>
    </>;
};
