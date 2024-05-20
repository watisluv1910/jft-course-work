import React from 'react';
import {createRoot} from 'react-dom/client';
import {BrowserRouter} from 'react-router-dom';

import {App} from './App';

const container = document.getElementById('root');
const root = createRoot(container);

root.render(
    <BrowserRouter>
        <App/>
    </BrowserRouter>,
);

const devMode = process.env.NODE_ENV === 'development';

/**
 * Allow changes acceptance through hot
 */
if (devMode && module && module.hot) {
    module.hot.accept();
}
