package com.cms.cmsapp.common.utils.ChartService;

import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

public class SvgToImageConverter {

    private SvgToImageConverter() {}

    public byte[] convert(String svgContent,
                                 float width,
                                 float height) throws Exception {

        svgContent = svgContent.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");

        TranscoderInput input =
                new TranscoderInput(new StringReader(svgContent));

        try (ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            TranscoderOutput output =
                    new TranscoderOutput(outputStream);

            PNGTranscoder transcoder = new PNGTranscoder();

            // Set image size explicitly
            transcoder.addTranscodingHint(
                    PNGTranscoder.KEY_WIDTH, width);

            transcoder.addTranscodingHint(
                    PNGTranscoder.KEY_HEIGHT, height);

            // Optional: white background instead of transparent
            transcoder.addTranscodingHint(
                    PNGTranscoder.KEY_BACKGROUND_COLOR,
                    java.awt.Color.WHITE
            );

            transcoder.transcode(input, output);

            return outputStream.toByteArray();
        }
    }
}
