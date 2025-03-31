import axios from "axios"
import { useSelector } from "react-redux";
import { getUserUrl, refreshTokenUrl } from "../components/Urls";
import { refreshToken } from "../store/reducers/TokenSlice"

export async function CheckToken(token) {

    try {
        await axios.get(getUserUrl, { headers: { Authorization: `Bearer ${token}` } })
        return true
    } catch (e) {
        return false
    }
}

export async function RefreshToken(dispatch, config) {
    try {
        await axios.post(refreshTokenUrl, config).then(res => {
            dispatch(refreshToken(res.data))
        });
        return true
    } catch (e) {
        return false
    }
}