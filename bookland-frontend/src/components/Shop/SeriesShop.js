import React from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import '../../index.css'
import { BookFinder } from '../Finders/BookFinder';

export const SeriesShop = () =>{
    const { series_id } = useParams();

    return (
        <div className='mainBodyStyle' >
            <BookFinder seriesId={series_id}/>
        </div>
    )
}