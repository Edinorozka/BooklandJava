import axios from "axios"
import { authUrl, getUserUrl, refreshTokenUrl, createUrl } from "../components/Urls";
import { getToken, deleteToken } from "../store/reducers/TokenSlice"
import { getUser, deleteUser } from "../store/reducers/UserSlice"

export const Login = async (dispatch, config) => {
    try {
        await axios.post(authUrl, config).then(res => {
            console.log(res.data.token)
            dispatch(getToken(res.data))
        });
    } catch (e) {
        console.log(e)
    }
}

export async function GetUser(dispatch, token, login) {
    try {
        await axios.get(getUserUrl + login, { headers: { Authorization: `Bearer ${token}` } })
            .then(res => {
                dispatch(getUser(res.data))
            })
    } catch (e) {
        console.log(e)
    }
}

export async function RegUser(dispatch, user){
    try {
        await axios.post(createUrl, user).then(res => {
            console.log(res.data)
        });
    } catch (e) {
        console.log(e)
    }
}

export async function RefreshToken(dispatch, refreshToken){
    try {
        await axios.post(refreshTokenUrl, { refreshToken }).then(res => {
            dispatch(getToken(res.data))
        })
    } catch (e) {
        console.log(e)
    }
}
