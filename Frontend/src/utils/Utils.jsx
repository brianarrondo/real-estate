export default class Utils {
    static generateKey(prefix) {
        return `${ prefix }_${ new Date().getTime() }`;
    }

    static getCurrentDate() {
        return new Date().toISOString().split("T")[0];
    }

    static getFormattedDate(strDate) {
        return new Date(strDate).toLocaleDateString();
    }

    static generateUtcDate(year, month, day, hours = 0, minutes = 0, seconds = 0, milli = 0) {
        const date = new Date(new Date(year, month, day, hours, minutes, seconds, milli).toUTCString())
        new Date(new Date().toUTCString());
        date.setUTCHours(hours, minutes, seconds, milli);
        return date;
    }

    static convertDateToUTC(date) {
        return new Date(date.getUTCFullYear(), date.getUTCMonth(), date.getUTCDate(), date.getUTCHours(), date.getUTCMinutes(), date.getUTCSeconds());
    }
}