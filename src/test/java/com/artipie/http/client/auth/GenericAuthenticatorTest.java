/*
 * MIT License
 *
 * Copyright (c) 2020 Artipie
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.artipie.http.client.auth;

import com.artipie.http.Headers;
import com.artipie.http.headers.Authorization;
import com.artipie.http.headers.WwwAuthenticate;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link GenericAuthenticator}.
 *
 * @since 0.3
 */
class GenericAuthenticatorTest {

    @Test
    void shouldProduceNothingWhenNoAuthRequested() {
        MatcherAssert.assertThat(
            new GenericAuthenticator("alice", "qwerty").authenticate(Headers.EMPTY),
            new IsEqual<>(Headers.EMPTY)
        );
    }

    @Test
    void shouldProduceBasicHeaderWhenRequested() {
        MatcherAssert.assertThat(
            StreamSupport.stream(
                new GenericAuthenticator("Aladdin", "open sesame").authenticate(
                    new Headers.From(new WwwAuthenticate("Basic"))
                ).spliterator(),
                false
            ).map(Map.Entry::getKey).collect(Collectors.toList()),
            Matchers.contains(Authorization.NAME)
        );
    }
}