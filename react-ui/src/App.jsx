import React, {useEffect, useState} from 'react';
import {Link} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import {StoriesContainer} from './components/containers/StoriesContainer';
import {eventBus} from './utils/eventBus';
import {AuthService} from './services/auth.service';
import {Route, Routes} from 'react-router';
import {Profile} from './components/Profile';
import {Register} from './components/Register';
import {Login} from './components/Login';
import {GlobalStyle} from './assets/styles/ResetStyles';
import {BoardModerator} from './components/boards/BoardModerator';
import {BoardAuthor} from './components/boards/BoardAuthor';
import {BoardAdmin} from './components/boards/BoardAdmin';
import {BookmarkService} from './services/bookmark.service';
import {ArticleBookmarksContext} from './context/useBookmarksContext';
import sunIcon from './assets/icons/sun_favicon_round.png';

export const App = () => {
    const [showModeratorBoard, setShowModeratorBoard] = useState(false);
    const [showAdminBoard, setShowAdminBoard] = useState(false);
    const [currentUser, setCurrentUser] = useState(undefined);
    const [bookmarks, setBookmarks] = useState([]);

    useEffect(() => {
        const user = AuthService.getCurrentUser();

        if (user) {
            setCurrentUser(user);
            setShowModeratorBoard(user.roles.includes('ROLE_MODERATOR'));
            setShowAdminBoard(user.roles.includes('ROLE_ADMIN'));

            BookmarkService
                .getUserBookmarks()
                .then((data) => setBookmarks(data));
        }

        eventBus.on('logout', handleLogout);

        return () => {
            eventBus.remove('logout', handleLogout);
        };
    }, []);

    const handleLogout = () => {
        AuthService.logout().then();

        localStorage.clear();

        setShowModeratorBoard(false);
        setShowAdminBoard(false);
        setCurrentUser(undefined);
    };

    return (
        <>
            <ArticleBookmarksContext.Provider
                value={{bookmarks, setBookmarks}}>
                <GlobalStyle/>
                <div>
                    <nav className="navbar navbar-expand navbar-dark bg-dark">
                        <img
                            src={sunIcon}
                            alt="Sun Icon"
                            className="mr-2"
                            style={{height: '30px', paddingRight: '10px'}}
                        />
                        <Link to={'/'} className="navbar-brand">
                            The Sun News Stories
                        </Link>
                        <div className="navbar-nav mr-auto">
                            {showAdminBoard && (
                                <li className="nav-item">
                                    <Link to={'/board/admin'}
                                          className="nav-link">
                                        Admin Board
                                    </Link>
                                </li>
                            )}

                            {showModeratorBoard && (
                                <li className="nav-item">
                                    <Link to={'/board/moderator'}
                                          className="nav-link">
                                        Moderator Board
                                    </Link>
                                </li>
                            )}

                            {currentUser && (
                                <li className="nav-item">
                                    <Link to={'/board/author'}
                                          className="nav-link">
                                        Author Board
                                    </Link>
                                </li>
                            )}
                        </div>

                        {currentUser ? (
                            <div className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link to={'/profile'}
                                          state={{
                                              username: currentUser.username,
                                              userEmail: currentUser.userEmail,
                                          }}
                                          className="nav-link">
                                        {currentUser.username}
                                    </Link>
                                </li>
                                <li className="nav-item">
                                    <a
                                        href="/"
                                        className="nav-link"
                                        onClick={() => eventBus.dispatch('logout', null)}
                                    >
                                        Logout
                                    </a>
                                </li>
                            </div>
                        ) : (
                            <div className="navbar-nav ml-auto">
                                <li className="nav-item">
                                    <Link to={'/login'}
                                          className="nav-link">
                                        Login
                                    </Link>
                                </li>

                                <li className="nav-item">
                                    <Link to={'/register'}
                                          className="nav-link">
                                        Register
                                    </Link>
                                </li>
                            </div>
                        )}
                    </nav>


                    <div className={"mt-4"}>
                        <Routes>
                            <Route
                                exact path={'/'}
                                element={<StoriesContainer/>}
                            />
                            <Route
                                exact path="/login"
                                element={<Login/>}/>
                            <Route
                                exact path="/register"
                                element={<Register/>}/>
                            <Route
                                exact path="/profile"
                                element={<Profile/>}/>
                            <Route exact path="/board/author"
                                   element={<BoardAuthor/>}/>
                            <Route exact path="/board/moderator"
                                   element={<BoardModerator/>}/>
                            <Route exact path="/board/admin"
                                   element={<BoardAdmin/>}/>
                        </Routes>
                    </div>
                </div>
            </ArticleBookmarksContext.Provider>
        </>
    );
};
