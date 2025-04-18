import axios from "axios"
import { changeReviews, createReviews, deleteReviews, getReviews, getReviewsSize } from "../components/Urls";

export const GetReviewsSize = async (isbn) => {
    try{
        const config = {
            params: {
                isbn: isbn
            }
        }
        const res = await axios.get(getReviewsSize, config);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const GetReviews = async (page, isbn) => {
    try{
        const config = {
            params: {
                page: page,
                isbn: isbn
            }
        }
        const res = await axios.get(getReviews, config);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const CreateReview = async (review, token) => {
    try{
        await axios.post(createReviews, review,
            { headers: { Authorization: `Bearer ${token}` } }).then(res => {
            return "Статья опубликованна"
        })
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const ChangeReview = async (review, token) => {
    try{
        await axios.put(changeReviews, review,
            { headers: { Authorization: `Bearer ${token}` } }).then(res => {
            return "Статья изменена"
        })
    } catch (e) {
        console.log(e)
        return 0
    } 
}

export const DeleteReview = async (id, token) => {
    try{
        const config = {
            params: {
                reviewId: id
            },
            headers: { Authorization: `Bearer ${token}` }
        }
        await axios.delete(deleteReviews, config)
            return "Статья удалена"
    } catch (e) {
        console.log(e)
        return 0
    } 
}