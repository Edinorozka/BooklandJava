import React, {useState, useEffect} from 'react'
import { useNavigate } from 'react-router-dom'
import { Menu, Avatar, Button, Dropdown, Select } from 'antd'
import { SearchOutlined } from '@ant-design/icons';
import { GetBooks, GetThreeBooks } from '../../api/FindBooksApiMethods'
import { getBookImage } from '../Urls';
import { GetThreeArticles } from '../../api/BlogApiMethods';

export const Finder = () => {
    const navigation = useNavigate()
    const { Option, OptGroup  } = Select;
    const [searchText, setSearchText] = useState('');
    const [books, setBooks] = useState([])
    const [articles, setArticles] = useState([])
    const [params, setParams] = useState({
                type: 0,
                genre: 0,
                publisher: 0,
                series: 0,
                inName: '',
                author: 0,
                lowPrise: 0,
                highPrise: 0,
                page: 0,
            })

    const getInfo = async () => {
        const res = await GetThreeBooks(params);
        setBooks(res)
        const a = await GetThreeArticles(params.inName);
        setArticles(a)
    }

    useEffect(() => {
        getInfo()
    }, [params]);

    const CustomLabel = ({ label, onButtonClick }) => (
        <div style={{ display: 'flex', alignItems: 'center' }}>
            <p style={{fontSize: "16px"}}>{label}</p>
            <Button type='primary' style={{ marginLeft: 8 }} onClick={onButtonClick}>
                Больше
            </Button>
        </div>
    );
    
    const handleChange = (value) => {
        if (value <= 3)
            navigation(`/${value}`);
        else
            navigation(`/blog/${value}`);
    };
    
    return (
        <Select
        showSearch
        suffixIcon={<SearchOutlined />}
        value={searchText}
        onChange={handleChange}
        style={{ width: "100%" }}
        onSearch={(value) => {setParams((prevParams) => ({ ...prevParams, inName: value }))}}
        filterOption={false}
      >
        {books && books.length > 0 &&
            <OptGroup label={<CustomLabel label="Книги" onButtonClick={() => navigation(`/shop/find/${params.inName}`)}/>}>
                {books.map((book, index) => {
                    return <Option value = {book.isbn} key={index}>
                        <div style={{display: "flex"}}>
                            <img  src={getBookImage + book.images[0].location} style={{width: "30px", marginRight: 10}}/>
                            <div>
                                <p style={{margin: 0}}>{book.name}</p>
                                <p style={{margin: 0, color: "grey"}}>{book.authors.map(author => `${author.name} ${author.lastName}`).join(', ')}</p>
                            </div>
                        </div>
                    </Option>
                })}
            </OptGroup>
        }
        
        {articles.length > 0 &&
            <OptGroup label={<CustomLabel label="Статьи" onButtonClick={() => {params.inName !== '' ? navigation(`/blog/find/${params.inName}`) : navigation(`/blog`) }}/>}>
                {articles.map((article, index) => {
                    return <Option value = {article.id} key={index + 3}>{article.title}</Option>
                })}
            </OptGroup>
        }
        
      </Select>
    )
}