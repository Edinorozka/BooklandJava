import axios from "axios"
import { blogFindThreeArticleUrl, blogUrlMain, blogUrlSize, createArticle, createComment, deleteArticleUrl, deleteCommentUrl, getComments, openArticle, updateArticleUrl, updateCommentUrl } from "../components/Urls";

export const GetArticlesSize = async (typeArticles) => {
    try{
        let config;
        if (typeArticles !== 'ALL'){
            config = {
                params: {
                    type: typeArticles
                }
            }
        }
        const res = await axios.get(blogUrlSize, config);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetArticles = async (sortType, typeArticles, current, find) => {
    try {
        let config;
        if (typeArticles === 'ALL'){
            config = {
                params: {
                    sort: sortType,
                    current: current,
                    find: find
                }
            }
        } else {
            config = {
                params: {
                    sort: sortType,
                    current: current,
                    type: typeArticles,
                    find: find
                }
            }
        }
        const res = await axios.get(blogUrlMain, config);
        return res.data
    } catch (e) {
        console.log(e)
        return null
    }
}

export const GetThreeArticles = async (find) => {
    try {
        const config = {
            params: {
                sort: true,
                current: 0,
                find: find
            }
        }
            
        const res = await axios.get(blogFindThreeArticleUrl, config);
        return res.data
    } catch (e) {
        console.log(e)
        return null
    }
}

export const GetArticle = async (id) => {
    try {
        const res = await axios.get(openArticle + id);
        return res.data
    } catch (e) {
        console.log(e)
        return null
    }
}

export async function GetMaterial(dispatch, material){
    try {
        await axios.get(blogUrlMain + material).then(res => {
            return res.data
        })
    } catch (e) {
        console.log(e)
    }
}

export async function CreateArticle(config, token){
    try {
        await axios.post(createArticle, config,
            { headers: { Authorization: `Bearer ${token}` } }).then(res => {
            return "Статья опубликованна"
        })
    } catch (e) {
        return(e.status)
    }
}

export async function UpdateArticle(config, token){
    try {
        await axios.put(updateArticleUrl, config,
            { headers: { Authorization: `Bearer ${token}` } }).then(res => {
            return "Статья изменена"
        })
    } catch (e) {
        return(e.status)
    }
}

export async function DeleteArticle(id, token){
    try {
        await axios.delete(deleteArticleUrl + id, { headers: { Authorization: `Bearer ${token}` } }).then(res => {
            return "коментарий удалён"
        })
    } catch (e) {
        return(e.status)
    }
}

export const GetComments = async (id) => {
    try {
        const res = await axios.get(getComments + id);
        return res.data
    } catch (e) {
        console.log(e)
        return null
    }
}

export async function CreateComment(config, token){
    try {
        await axios.post(createComment, config,
            { headers: { Authorization: `Bearer ${token}` } }).then(res => {
            return "коментарий опубликованн"
        })
    } catch (e) {
        return(e.status)
    }
}

export async function UpdateComment(comment, token) {
    try{
        await axios.put(updateCommentUrl, comment, {
            headers: {
                Authorization: `Bearer ${token}`
            }
        }).then(res => {
            return "коментарий изменён"
        })
    } catch (e){
        return(e.status)
    }
}

export async function DeleteComment(id, token) {
    try{
        await axios.delete(deleteCommentUrl + id, { headers: { Authorization: `Bearer ${token}` } }).then(res => {
            return "коментарий удалён"
        })
    } catch (e) {
        return(e.status)
    }
    
}