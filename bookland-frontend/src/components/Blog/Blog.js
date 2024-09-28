import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { blogUrlMain } from '../Urls';



export const Blog = () => {
    const [data, setData] = useState([]);
    

    const getData = async () => {
        try {
            const response = await axios.get(blogUrlMain);
            setData(response.data);
        } catch (error) {
            console.error(error);
        }
    }

    useEffect(() => {
        getData()
    }, []);
    


    return (
        <p>{ data.text }</p>
        )
}
