import axios from "axios"
import { getBanners } from "../components/Urls";

export const GetBanners = async () => {
    try{
        const res = await axios.get(getBanners);
        return res.data
    } catch (e) {
        console.log(e)
        return 0
    } 
}