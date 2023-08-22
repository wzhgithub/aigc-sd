package com.yiyun.ai.core.api.business.sd;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class SDAny2ImageStruct {


    @NoArgsConstructor
    @Data
    public static class SDTxt2ImageResponse {

        @SerializedName("images")
        private List<String> images;
        @SerializedName("parameters")
        private ParametersDTO parameters;
        @SerializedName("info")
        private String info;

        @NoArgsConstructor
        @Data
        public static class ParametersDTO {
            @SerializedName("enable_hr")
            private Boolean enableHr;
            @SerializedName("denoising_strength")
            private long denoisingStrength;
            @SerializedName("firstphase_width")
            private long firstphaseWidth;
            @SerializedName("firstphase_height")
            private long firstphaseHeight;
            @SerializedName("hr_scale")
            private Double hrScale;
            @SerializedName("hr_upscaler")
            private Object hrUpscaler;
            @SerializedName("hr_second_pass_steps")
            private long hrSecondPassSteps;
            @SerializedName("hr_resize_x")
            private long hrResizeX;
            @SerializedName("hr_resize_y")
            private long hrResizeY;
            @SerializedName("prompt")
            private String prompt;
            @SerializedName("styles")
            private Object styles;
            @SerializedName("seed")
            private long seed;
            @SerializedName("subseed")
            private long subseed;
            @SerializedName("subseed_strength")
            private long subseedStrength;
            @SerializedName("seed_resize_from_h")
            private long seedResizeFromH;
            @SerializedName("seed_resize_from_w")
            private long seedResizeFromW;
            @SerializedName("sampler_name")
            private Object samplerName;
            @SerializedName("batch_size")
            private long batchSize;
            @SerializedName("n_iter")
            private long nIter;
            @SerializedName("steps")
            private long steps;
            @SerializedName("cfg_scale")
            private Double cfgScale;
            @SerializedName("width")
            private long width;
            @SerializedName("height")
            private long height;
            @SerializedName("restore_faces")
            private Boolean restoreFaces;
            @SerializedName("tiling")
            private Boolean tiling;
            @SerializedName("do_not_save_samples")
            private Boolean doNotSaveSamples;
            @SerializedName("do_not_save_grid")
            private Boolean doNotSaveGrid;
            @SerializedName("negative_prompt")
            private Object negativePrompt;
            @SerializedName("eta")
            private Object eta;
            @SerializedName("s_min_uncond")
            private Double sMinUncond;
            @SerializedName("s_churn")
            private Double sChurn;
            @SerializedName("s_tmax")
            private Object sTmax;
            @SerializedName("s_tmin")
            private Double sTmin;
            @SerializedName("s_noise")
            private Double sNoise;
            @SerializedName("override_settings")
            private Object overrideSettings;
            @SerializedName("override_settings_restore_afterwards")
            private Boolean overrideSettingsRestoreAfterwards;
            @SerializedName("script_args")
            private List<?> scriptArgs;
            @SerializedName("sampler_index")
            private String samplerIndex;
            @SerializedName("script_name")
            private Object scriptName;
            @SerializedName("send_images")
            private Boolean sendImages;
            @SerializedName("save_images")
            private Boolean saveImages;
            @SerializedName("alwayson_scripts")
            private AlwaysonScriptsDTO alwaysonScripts;

            @NoArgsConstructor
            @Data
            public static class AlwaysonScriptsDTO {
            }
        }
    }

    /**
     * base on <a href="https://juejin.cn/post/7265666505101164603">...</a>
     */
    @NoArgsConstructor
    @Data
    public static class SDTxt2ImageRequest {

        @SerializedName("alwayson_scripts")
        private AlwaysonScriptsDTO alwaysonScripts;
        @SerializedName("batch_size")
        private long batchSize;
        @SerializedName("cfg_scale")
        private long cfgScale;
        @SerializedName("height")
        private long height;
        @SerializedName("negative_prompt")
        private String negativePrompt;
        @SerializedName("override_settings")
        private OverrideSettingsDTO overrideSettings;
        @SerializedName("clip_skip")
        private long clipSkip;
        @SerializedName("prompt")
        private String prompt;
        @SerializedName("restore_faces")
        private Boolean restoreFaces;
        @SerializedName("sampler_index")
        private String samplerIndex;
        @SerializedName("sampler_name")
        private String samplerName;
        @SerializedName("script_args")
        private List<?> scriptArgs;
        @SerializedName("seed")
        private Long seed;
        @SerializedName("steps")
        private long steps;
        @SerializedName("tiling")
        private Boolean tiling;
        @SerializedName("width")
        private long width;

        @NoArgsConstructor
        @Data
        public static class AlwaysonScriptsDTO {
            @SerializedName("controlnet")
            private ControlnetDTO controlnet;

            @NoArgsConstructor
            @Data
            public static class ControlnetDTO {
                @SerializedName("args")
                private List<ArgsDTO> args;

                @NoArgsConstructor
                @Data
                public static class ArgsDTO {
                    @SerializedName("control_mode")
                    private long controlMode;
                    @SerializedName("enabled")
                    private Boolean enabled;
                    @SerializedName("guidance_end")
                    private Double guidanceEnd;
                    @SerializedName("guidance_start")
                    private Double guidanceStart;
                    @SerializedName("input_image")
                    private String inputImage;
                    @SerializedName("lowvram")
                    private Boolean lowvram;
                    @SerializedName("model")
                    private String model;
                    @SerializedName("module")
                    private String module;
                    @SerializedName("pixel_perfect")
                    private Boolean pixelPerfect;
                    @SerializedName("processor_res")
                    private long processorRes;
                    @SerializedName("resize_mode")
                    private long resizeMode;
                    @SerializedName("threshold_a")
                    private long thresholdA;
                    @SerializedName("threshold_b")
                    private long thresholdB;
                    @SerializedName("weight")
                    private Double weight;
                }
            }
        }

        @NoArgsConstructor
        @Data
        public static class OverrideSettingsDTO {
            @SerializedName("sd_model_checkpoint")
            private String sdModelCheckpoint;
            @SerializedName("sd_vae")
            private String sdVae;
        }
    }

    @NoArgsConstructor
    @Data
    public static class SDImage2ImageRequest {

        @SerializedName("init_images")
        private List<String> initImages;
        @SerializedName("resize_mode")
        private long resizeMode;
        @SerializedName("denoising_strength")
        private Double denoisingStrength;
        @SerializedName("image_cfg_scale")
        private long imageCfgScale;
        @SerializedName("mask")
        private String mask;
        @SerializedName("mask_blur")
        private long maskBlur;
        @SerializedName("mask_blur_x")
        private long maskBlurX;
        @SerializedName("mask_blur_y")
        private long maskBlurY;
        @SerializedName("inpainting_fill")
        private long inpaintingFill;
        @SerializedName("inpaint_full_res")
        private Boolean inpaintFullRes;
        @SerializedName("inpaint_full_res_padding")
        private long inpaintFullResPadding;
        @SerializedName("inpainting_mask_invert")
        private long inpaintingMaskInvert;
        @SerializedName("initial_noise_multiplier")
        private long initialNoiseMultiplier;
        @SerializedName("prompt")
        private String prompt;
        @SerializedName("styles")
        private List<String> styles;
        @SerializedName("seed")
        private long seed;
        @SerializedName("subseed")
        private long subseed;
        @SerializedName("subseed_strength")
        private long subseedStrength;
        @SerializedName("seed_resize_from_h")
        private long seedResizeFromH;
        @SerializedName("seed_resize_from_w")
        private long seedResizeFromW;
        @SerializedName("sampler_name")
        private String samplerName;
        @SerializedName("batch_size")
        private long batchSize;
        @SerializedName("n_iter")
        private long nIter;
        @SerializedName("steps")
        private long steps;
        @SerializedName("cfg_scale")
        private long cfgScale;
        @SerializedName("width")
        private long width;
        @SerializedName("height")
        private long height;
        @SerializedName("restore_faces")
        private Boolean restoreFaces;
        @SerializedName("tiling")
        private Boolean tiling;
        @SerializedName("do_not_save_samples")
        private Boolean doNotSaveSamples;
        @SerializedName("do_not_save_grid")
        private Boolean doNotSaveGrid;
        @SerializedName("negative_prompt")
        private String negativePrompt;
        @SerializedName("eta")
        private long eta;
        @SerializedName("s_min_uncond")
        private long sMinUncond;
        @SerializedName("s_churn")
        private long sChurn;
        @SerializedName("s_tmax")
        private long sTmax;
        @SerializedName("s_tmin")
        private long sTmin;
        @SerializedName("s_noise")
        private long sNoise;
        @SerializedName("override_settings")
        private OverrideSettingsDTO overrideSettings;
        @SerializedName("override_settings_restore_afterwards")
        private Boolean overrideSettingsRestoreAfterwards;
        @SerializedName("script_args")
        private List<?> scriptArgs;
        @SerializedName("sampler_index")
        private String samplerIndex;
        @SerializedName("include_init_images")
        private Boolean includeInitImages;
        @SerializedName("script_name")
        private String scriptName;
        @SerializedName("send_images")
        private Boolean sendImages;
        @SerializedName("save_images")
        private Boolean saveImages;
        @SerializedName("alwayson_scripts")
        private AlwaysonScriptsDTO alwaysonScripts;

        @NoArgsConstructor
        @Data
        public static class OverrideSettingsDTO {
        }

        @NoArgsConstructor
        @Data
        public static class AlwaysonScriptsDTO {
        }
    }

    @NoArgsConstructor
    @Data
    public static class SDImage2ImageResponse {

        @SerializedName("images")
        private List<String> images;
        @SerializedName("parameters")
        private ParametersDTO parameters;
        @SerializedName("info")
        private String info;

        @NoArgsConstructor
        @Data
        public static class ParametersDTO {
        }
    }
}
