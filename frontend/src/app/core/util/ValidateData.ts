
// eslint-disable-next-line prefer-arrow/prefer-arrow-functions
export function isNull(data: any): boolean {
    return data === null;
}

// eslint-disable-next-line prefer-arrow/prefer-arrow-functions
export function isNullUndefined(data: any): boolean  {
    return data === undefined;
}

// eslint-disable-next-line prefer-arrow/prefer-arrow-functions
export function isNullBlankUndefined(data: any): boolean  {
    return data === null || data === undefined || data === '';
}

// eslint-disable-next-line prefer-arrow/prefer-arrow-functions
export function isNullBlank(data: any): boolean  {
    return data == null || data === '';
}

// eslint-disable-next-line prefer-arrow/prefer-arrow-functions
export function isNullBlankZeroUndefined(data: any): boolean  {
    return data == null || data === '' || data === undefined || data === 0;
}

// eslint-disable-next-line prefer-arrow/prefer-arrow-functions
export function isObject(val: any): boolean  {
    if (val === null) {
        return false;
    }
    return ((typeof val === 'function') || (typeof val === 'object'));
}
