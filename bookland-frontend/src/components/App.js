import React from 'react'
import { Route, Routes } from "react-router-dom";
import { ConfigProvider } from 'antd';
import { Navbar } from "./Navbar/Havbar";
import { About } from "./about/About"
import { NotFound } from "./NotFound/NotFound";
import { Blog } from "./Blog/Blog";
import { Authorization } from './User/Authorization';
import { Registration } from './User/Registration';
import { CreateNewPost } from './Blog/CreateNewPost';
import { Article } from './Blog/Article';
import { Shop } from './Shop/Shop';
import { AuthorShop } from './Shop/AuthorShop';
import { GenreShop } from './Shop/GenreShop';
import { SeriesShop } from './Shop/SeriesShop';
import { SearchBookShop } from './Shop/SearchBookShop';
import { Card } from './Shop/Card';
import { FindShop } from './Shop/FindShop';
import { ShoppingCard } from './buy/ShoppingCart';
import { Purchases } from './buy/Purchases';
import { Setting } from './Settings/Settings';

function App() {
    return (
        <div>
            <ConfigProvider
                    theme={{
                        token: {
                            colorPrimary: '#7146bd',
                            colorBorderSecondary: '#FFFFFF',
                            boxShadow: "#7146bd",
                        },
                        components: {
                            Carousel: {
                                arrowSize: 30
                            }
                        }
                    }}
                >
                    <Navbar>
                        <Routes>
                            <Route path={'/'} element={<Shop />} />
                            <Route path={'/card/:card_id'} element={<Card />} />
                            <Route path={'/shop/author/:author_id'} element={<AuthorShop />} />
                            <Route path={'/shop/genre/:genre_id'} element={<GenreShop />} />
                            <Route path={'/shop/series/:series_id'} element={<SeriesShop />} />
                            <Route path={'/shop/search/:search'} element={<SearchBookShop />} />
                            <Route path={'/shop/find/:text'} element={<FindShop />} />
                            <Route path={'/shop/find/'} element={<FindShop />} />
                            <Route path={'/shop/cart'} element={<ShoppingCard />} />
                            <Route path={'/purchases'} element={<Purchases />} />
                            <Route path="/blog" element={<Blog />} />
                            <Route path="/blog/find/:find" element={<Blog />} />
                            <Route path="/blog/find/" element={<Blog />} />
                            <Route path="/blog/:article_id" element={<Article />} />
                            <Route path={'/createPost'} element={<CreateNewPost />} />
                            <Route path={'/editPost/:article_id'} element={<CreateNewPost />} />
                            <Route path={'/about'} element={<About />} />
                            <Route path={'/login'} element={<Authorization />} />
                            <Route path={'/registration'} element={<Registration />} />
                            <Route path={'/settings'} element={<Setting />} />
                            <Route path={'*'} element={<NotFound />} />
                        </Routes>
                    </Navbar>
                </ConfigProvider>
        </div>
        
    );
}

export default App;
