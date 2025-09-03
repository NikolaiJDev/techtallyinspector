import React from 'react'

const EquipmentPage: React.FC = () => {
  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <h1 className="text-3xl font-bold text-gray-900">Equipment Management</h1>
        <button className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
          Add Equipment
        </button>
      </div>

      <div className="bg-white rounded-lg shadow-sm p-6 border border-gray-200">
        <p className="text-gray-600">
          Equipment management functionality will be implemented here.
          This will include equipment listing, filtering, CRUD operations, and maintenance tracking.
        </p>
      </div>
    </div>
  )
}

export default EquipmentPage