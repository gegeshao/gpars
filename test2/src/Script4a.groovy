/**
 * Created by root on 9/16/19.
 */


class Factory {
    public Binding DeviceTypeBinding() {
        Binding devTypeBinding = new Binding()
        devTypeBinding.DeviceType = { devType ->
            if (devType.isVirtual == null)
                addDeviceType(devType.id, devType.base, devType.isVirtual)
            else
                addDeviceType(devType.id, devType.base, devType.isVirtual)
        }
        return devTypeBinding
    }
}
