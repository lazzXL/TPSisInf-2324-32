package isel.sisinf.jpa.repo;


import isel.sisinf.model.GPSDevice;
import java.util.Collection;

public interface IGPSDeviceRepository extends IRepository<GPSDevice, Collection<GPSDevice>, Long>, IGPSDeviceDataMapper{
}
