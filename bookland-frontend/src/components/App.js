import React from 'react'
import { Route, Routes } from "react-router-dom";
import { ConfigProvider } from 'antd';
import { Navbar } from "./Navbar/Havbar";
import { About } from "./about/About"
import { NotFound } from "./NotFound/NotFound";
import { Blog } from "./Blog/Blog";
import { Authorization } from './Login/Authorization';
import { Registration } from './Login/Registration';
import { CreateNewPost } from './Blog/CreateNewPost';
import { Article } from './Blog/Article';
import { Shop } from './Shop/Shop';

function App() {
    return (
        <div>
            <ConfigProvider
                    theme={{
                        token: {
                            colorPrimary: '#7146bd',
                            colorBorderSecondary: '#FFFFFF',
                            boxShadow: "#7146bd",
                        }
                    }}
                >
                    <Navbar>
                        <Routes>
                            <Route path={'/'} element={<Shop />} />
                            <Route path="/blog" element={<Blog />} />
                            <Route path="/blog/:article_id" element={<Article />} />
                            <Route path={'/createPost'} element={<CreateNewPost />} />
                            <Route path={'/editPost/:article_id'} element={<CreateNewPost />} />
                            <Route path={'/about'} element={<About />} />
                            <Route path={'/login'} element={<Authorization />} />
                            <Route path={'/registration'} element={<Registration />} />
                            <Route path={'*'} element={<NotFound />} />
                        </Routes>
                    </Navbar>
                </ConfigProvider>
        </div>
        
    );
}

export default App;
