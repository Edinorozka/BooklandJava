import React, {useState, useEffect} from 'react'
import { useNavigate } from 'react-router-dom'
import { Menu, Avatar, Button, Dropdown, Select } from 'antd'
import { GetBooks } from '../../api/FindBooksApiMethods'

export const Finder = () => {
    const navigation = useNavigate()
    const { Option, OptGroup  } = Select;
    const [searchText, setSearchText] = useState('');
    const [books, setBooks] = useState([])
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
        const res = await GetBooks(params);
        setBooks(res)
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
        setSearchText(value);
        navigation(`/${value}`);
        console.log(value)
    };

    
    
    return (
        <Select
        showSearch
        
        value={searchText}
        onChange={handleChange}
        style={{ width: "100%" }}
        onInputKeyDown={(input) => {setParams((prevParams) => ({ ...prevParams, inName: input.target.value }))}}
        filterOption={false}
        dropdownStyle={{ height: 'auto', overflow: 'auto' }}
      >
        {books.length > 0 &&
            <OptGroup label={<CustomLabel label="Книги" onButtonClick={() => console.log("Книги")}/>}>
                {books.map((book) => {
                    return <Option value = {book.isbn} key={book.isbn} >{book.name} {book.authors.map(author => author.name + " " + author.lastName)}</Option>
                })}
            </OptGroup>
        }
        
        <OptGroup label={<CustomLabel label="Статьи" onButtonClick={() => console.log("Статьи")}/>}>
          <Option value="Option 3" key={100}>Опция 3</Option>
          <Option value="Option 4" key={110}>Опция 4</Option>
        </OptGroup>
      </Select>
    )
}