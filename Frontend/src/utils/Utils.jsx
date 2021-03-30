import baseService from "../services/BaseService";

export default class Utils {
    static generateKey(prefix) {
        return `${ prefix }_${ new Date().getTime() }`;
    }
}