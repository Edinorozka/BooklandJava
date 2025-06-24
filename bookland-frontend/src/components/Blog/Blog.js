import React from 'react'
import { BlogFinder } from '../Finders/BlogFinder';
import { useParams } from 'react-router-dom';


export const Blog = () => {
    const {find} = useParams();

    return (
        <div className='mainBodyStyle'>
            <BlogFinder find={find}/>
        </div>
    )
}
