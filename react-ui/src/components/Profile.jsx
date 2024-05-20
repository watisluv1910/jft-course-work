import {BookmarksContainer} from './containers/BookmarksContainer';
import {useLocation} from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

export const Profile = () => {
    const location = useLocation();
    const {username, userEmail} = location.state;
    return (
        <div className="container">
            <div className="card">
                <div className="card-body text-center">
                    <img
                        src="//ssl.gstatic.com/accounts/ui/avatar_2x.png"
                        alt="Profile ico"
                        className="rounded-circle m-auto"
                        style={{
                            width: '150px',
                            height: '150px',
                            marginBottom: '10px',
                        }}
                    />
                    <h5 className="card-title">Profile</h5>
                    <h6 className="card-subtitle mb-2 text-muted">
                        {username}
                    </h6>
                    <p className="card-text">{userEmail}</p>
                </div>
            </div>
            <div className="card mt-3 mb-3" style={{marginTop: 0}}>
                <div className="card-header" style={{textAlign: 'center'}}>
                    <h3>Saved news</h3>
                </div>
                <div className="card-body">
                    <BookmarksContainer/>
                </div>
            </div>
        </div>
    );
};
