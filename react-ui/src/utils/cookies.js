/**
 * Retrieves the value of a specific cookie from the document's cookie string.
 *
 * @function
 * @param {string} name - The name of the cookie to retrieve.
 * @return {string|null} The value of the cookie if it exists.
 * Otherwise, returns null.
 */
const getCookie = (name) => {
    let end;
    const dc = document.cookie;
    const prefix = name + '=';
    let begin = dc.indexOf('; ' + prefix);
    if (begin === -1) {
        begin = dc.indexOf(prefix);
        if (begin !== 0) return null;
    } else {
        begin += 2;
        end = document.cookie.indexOf(';', begin);
        if (end === -1) {
            end = dc.length;
        }
    }

    return decodeURI(dc.substring(begin + prefix.length, end));
};

const getCookieValue = (name) => {
    const cookies = document.cookie.split('; ');
    const cookie = cookies.find((c) => c.startsWith(`${name}=`));
    return cookie ? cookie.split('=')[1] : null;
};

export {getCookie, getCookieValue};
