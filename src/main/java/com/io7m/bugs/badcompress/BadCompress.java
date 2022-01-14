/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.bugs.badcompress;

import org.apache.commons.compress.compressors.lz4.FramedLZ4CompressorOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public final class BadCompress
{
  private BadCompress()
  {

  }

  public static void main(
    final String[] args)
    throws IOException
  {
    final var file =
      Paths.get("bad-compress.bin");
    final var bytes =
      Files.readAllBytes(file);

    if (bytes.length != 2097152) {
      throw new IllegalStateException();
    }

    final var timeThen = Instant.now();
    try (var byteOutput = new ByteArrayOutputStream()) {
      try (var lz4Stream = new FramedLZ4CompressorOutputStream(byteOutput)) {
        lz4Stream.write(bytes);
        lz4Stream.finish();
      }
    }
    final var timeNow = Instant.now();
    System.out.printf("Compressed in %s%n", Duration.between(timeThen, timeNow));
  }
}
