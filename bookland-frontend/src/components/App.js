import React from 'react'
import { Route, Routes } from "react-router-dom";
import { Navbar } from "./Navbar/Havbar";
import { About } from "./about/About"
import { NotFound } from "./NotFound/NotFound";
import { Blog } from "./Blog/Blog";
import { Authorization } from './Login/Authorization';
import { Registration } from './Login/Registration';

function App() {

    return (
        <div >
            <Navbar>
                <Routes>
                    <Route path={'/'} element={<p style={{ fontSize: "72px" }}>hello</p>} />
                    <Route path={'/blog'} element={<Blog />} />
                    <Route path={'/about'} element={<About />} />
                    <Route path={'/login'} element={<Authorization />} />
                    <Route path={'/registration'} element={<Registration />} />
                    <Route path={'*'} element={<NotFound />} />
                </Routes>
            </Navbar>
        </div>
        
    );
}

export default App;
