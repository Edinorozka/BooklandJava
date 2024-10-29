import axios from "axios"
import { blogUrlMain } from "../components/Urls";

export const GetArticles = async (sortType, typeArticles) => {
    try {
        let config;
        if (typeArticles == 'ALL'){
            config = {
                params: {
                    sort: sortType
                }
            }
        } else {
            config = {
                params: {
                    sort: sortType,
                    type: typeArticles
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