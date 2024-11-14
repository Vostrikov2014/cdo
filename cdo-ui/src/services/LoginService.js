import axios from "axios";

const BASE_URL_LOGIN = 'http://localhost:8090/login';

export const postLogin = () => axios.post(BASE_URL_LOGIN);