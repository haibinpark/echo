package com.sctuopuyi.echo.ext

import java.math.BigDecimal


fun CharSequence?.isNullOrZero(): Boolean {
    if (this == null || this.isBlank())
        return true
    if (BigDecimal(this.toString()) <= BigDecimal(0))
        return true
    return false
}