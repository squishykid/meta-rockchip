# Copyright (C) 2014 Romain Perier
# Released under the MIT license (see COPYING.MIT for the terms)

inherit kernel
require recipes-kernel/linux/linux-yocto.inc

TAG = "next-20141009"
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/next/linux-next.git;tag=${TAG} \
	file://defconfig"

LINUX_VERSION = "3.19"
# Override local version in order to use the one generated by linux build system
# And not "yocto-standard"
LINUX_VERSION_EXTENSION = ""
PR = "r1"
PV = "${LINUX_VERSION}+git-${TAG}"

# Include only supported boards for now
COMPATIBLE_MACHINE = "(radxa-rock|mars-board)"

# Build the devicetree blob in kernel_do_compile
KERNEL_ALT_IMAGETYPE = "${KERNEL_DEVICETREE_NAME}.dtb"
# The resulting image to be deployed in DEPLOY_IMAGE_DIR
KERNEL_OUTPUT = "${B}/arch/${ARCH}/boot/${KERNEL_IMAGETYPE}-dtb"

do_compile_append() {
    cat ${B}/arch/${ARCH}/boot/${KERNEL_IMAGETYPE} ${B}/arch/${ARCH}/boot/dts/${KERNEL_ALT_IMAGETYPE} > ${KERNEL_OUTPUT}
}
