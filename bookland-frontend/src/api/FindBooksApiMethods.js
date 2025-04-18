import axios from "axios"
import { getAllGenres, getAllparams, getAuthor, getAuthors, getBookUrl, getGenre, getGenres, getLimitsPrises, getOneSeries, getPublishers, getSeries, getTypes, shopSizeUrl, shopUrlMain } from "../components/Urls";

export const GetBooksSize = async (params) => {
    try{
        const config = {
            params: {
                type: params.type,
                genre: params.genre,
                publisher: params.publisher,
                series: params.series,
                inName: params.inName,
                author: params.author,
                lowPrise: params.lowPrise,
                highPrise: params.highPrise,
                page: params.page
            }
        }
        const res = await axios.get(shopSizeUrl, config);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetBooks = async (params) => {
    try{
        const config = {
            params: {
                type: params.type,
                genre: params.genre,
                publisher: params.publisher,
                series: params.series,
                inName: params.inName,
                author: params.author,
                lowPrise: params.lowPrise,
                highPrise: params.highPrise,
                page: params.page
            }
        }
        const res = await axios.get(shopUrlMain, config);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetOneBook = async (isbn) => {
    try{
        const res = await axios.get(getBookUrl + isbn);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    }
}

export const GetAllparams = async () => {
    try{
        const res = await axios.get(getAllparams);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetGenres = async (find) => {
    try{
        let res
        if (find){
            const config = {
                params: {
                    find: find
                }
            }
            res = await axios.get(getGenres, config);
        } else {
            res = await axios.get(getGenres);
        }
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetGenre = async (find) => {
    try{
        const config = {
            params: {
                find: find
            }
        }
        const res = await axios.get(getGenre, config);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetAllGenre = async () => {
    try{
        const res = await axios.get(getAllGenres);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetTypes = async (find) => {
    try{
        let res
        if (find){
            const config = {
                params: {
                    find: find
                }
            }
            res = await axios.get(getTypes, config);
        } else {
            res = await axios.get(getTypes);
        }
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetSeries = async (find) => {
    try{
        let res
        if (find){
            const config = {
                params: {
                    find: find
                }
            }
            res = await axios.get(getSeries, config);
        } else {
            res = await axios.get(getSeries);
        }
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetOneSeries = async (find) => {
    try{
        const config = {
            params: {
                find: find
            }
        }
        const res = await axios.get(getOneSeries, config);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetPublishers = async (find) => {
    try{
        let res
        if (find){
            const config = {
                params: {
                    find: find
                }
            }
            res = await axios.get(getPublishers, config);
        } else {
            res = await axios.get(getPublishers);
        }
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetAuthors = async (find) => {
    try{
        let res
        if (find){
            const config = {
                params: {
                    find: find
                }
            }
            res = await axios.get(getAuthors, config);
        } else {
            res = await axios.get(getAuthors);
        }
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetAuthor = async (find) => {
    try{
        const config = {
            params: {
                find: find
            }
        }
        const res = await axios.get(getAuthor, config);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetLimitsPrises = async (params) => {
    try{
        const config = {
            params: {
                type: params.type,
                genre: params.genre,
                publisher: params.publisher,
                series: params.series,
                inName: params.inName,
                author: params.author,
                page: params.page
            }
        }
        const res = await axios.get(getLimitsPrises, config);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}