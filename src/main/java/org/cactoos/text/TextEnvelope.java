/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017-2018 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.cactoos.text;

import java.io.IOException;
import org.cactoos.Scalar;
import org.cactoos.Text;
import org.cactoos.scalar.IoCheckedScalar;
import org.cactoos.scalar.UncheckedScalar;

/**
 * Text envelope that provides {@link #equals(Object)} and {@link #hashCode()}
 * methods.
 * @since 0.32
 * @checkstyle AbstractClassNameCheck (500 lines)
 */
public abstract class TextEnvelope implements Text {

    /**
     * String value of the envelope.
     */
    private final IoCheckedScalar<String> origin;

    /**
     * Ctor.
     * @param text Text representing the text value.
     */
    public TextEnvelope(final Text text) {
        this(new IoCheckedScalar<>(text::asString));
    }

    /**
     * Ctor.
     * @param scalar Scalar representing the text value.
     */
    public TextEnvelope(final Scalar<String> scalar) {
        this.origin = new IoCheckedScalar<>(scalar);
    }

    @Override
    public final String asString() throws IOException {
        return this.origin.value();
    }

    @Override
    public final String toString() {
        return new UncheckedText(this).asString();
    }

    @Override
    public final int hashCode() {
        return new UncheckedScalar<>(this.origin).value().hashCode();
    }

    // @todo #942:30min Refactor TextEnvelope.equals(). Current implementation
    //  of TextEnvelope.equals() uses some things that we must avoid, like more
    //  than one return on method, instance of usage and typecasting. Refactor
    //  this method so we can get rid of these smelly things.
    //
    @Override
    @SuppressWarnings("PMD.OnlyOneReturn")
    public final boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Text)) {
            return false;
        }
        final Text that = (Text) obj;
        return new UncheckedText(this).asString().equals(
            new UncheckedText(that).asString()
        );
    }
}
